package com.stslex.core.player.controller

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerState
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.player.player.AppPlayer
import com.stslex.core.player.player.SeekType
import com.stslex.core.player.worker.PreloadWorker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaServiceControllerImpl(
    private val player: AppPlayer,
    private val context: Context
) : MediaServiceController, Player.Listener {

    private val _simpleMediaState = MutableStateFlow(SimpleMediaState())
    override val simpleMediaState: StateFlow<SimpleMediaState>
        get() = _simpleMediaState.asStateFlow()

    private val _currentPlayingMedia = MutableStateFlow<MediaItem?>(null)
    override val currentPlayingMedia: StateFlow<MediaItem?>
        get() = _currentPlayingMedia.asStateFlow()

    private val _allMediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    override val allMediaItems: StateFlow<List<MediaItem>>
        get() = _allMediaItems.asStateFlow()

    private val mediaCache = mutableMapOf<String, Int>()
    private var job: Job = Job()
    private val workManager: WorkManager

    init {
        player.addListener(this)
        workManager = WorkManager.getInstance(context)
    }

    override fun addMediaItem(index: Int, mediaItem: MediaItem) {
        mediaCache[mediaItem.mediaId] = index
        player.addMediaItem(index, mediaItem)
        _allMediaItems.update { allItems ->
            allItems
                .toMutableList()
                .apply {
                    add(mediaItem)
                }
        }
    }

    override fun addMediaItems(items: List<MediaItem>) {
        items.forEachIndexed { index: Int, mediaItem: MediaItem ->
            addToWorker(mediaItem)
            addMediaItem(index, mediaItem)
        }
        player.prepare()
    }

    private fun addToWorker(mediaItem: MediaItem) {
        val url = mediaItem.requestMetadata.mediaUri?.toString().orEmpty()
        val workerRequest = PreloadWorker.buildWorkRequest(url)
        val workName = PreloadWorker.AUDIO_URL.plus(url)
        workManager.enqueueUniqueWork(workName, ExistingWorkPolicy.KEEP, workerRequest)
    }

    override suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            is PlayerEvent.Backward -> player.seek(SeekType.BACK)
            is PlayerEvent.Forward -> player.seek(SeekType.FORWARD)
            is PlayerEvent.Next -> player.seek(SeekType.NEXT)
            is PlayerEvent.Previous -> player.seek(SeekType.PREVIOUS)
            is PlayerEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                    stopProgressUpdate()
                } else {
                    player.play()
                    _simpleMediaState.update { currentState ->
                        currentState.copy(isPlaying = true)
                    }
                    startProgressUpdate()
                }
            }

            is PlayerEvent.ResumePause -> {
                if (simpleMediaState.value.playerState == PlayerState.Initial) {
                    currentPlayingMedia.value?.let { currentMedia ->
                        player.addMediaItem(currentMedia)
                        player.prepare()
                    }
                }
                player.playWhenReadyOrNot()
            }

            is PlayerEvent.Stop -> stopProgressUpdate()

            is PlayerEvent.UpdateProgress -> {
                val newProgress = player.duration * playerEvent.newProgress
                player.seekTo(newProgress.toLong())
            }

            is PlayerEvent.PlayPauseCurrent -> {
                mediaCache[playerEvent.id]?.let { index ->
                    player.seekTo(index, 0)
                    player.play()
                }
            }

            is PlayerEvent.SeekTo -> {
                if (player.currentMediaItem?.mediaId == playerEvent.id) return
                mediaCache[playerEvent.id]?.let { index ->
                    player.seekTo(index, 0)
                }
            }
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(10)
            _simpleMediaState.update { currentState ->
                currentState.copy(
                    progress = player.currentPosition
                )
            }
        }
    }

    private fun stopProgressUpdate() {
        job.cancel()
        _simpleMediaState.update { currentState ->
            currentState.copy(
                isPlaying = false
            )
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _simpleMediaState.update { currentState ->
                currentState.copy(
                    playerState = PlayerState.Buffering(player.currentPosition)
                )
            }

            ExoPlayer.STATE_READY -> _simpleMediaState.update { currentState ->
                currentState.copy(
                    duration = player.duration,
                    playerState = PlayerState.Content
                )
            }

            Player.STATE_ENDED -> Unit
            Player.STATE_IDLE -> Unit
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        _currentPlayingMedia.tryEmit(player.currentMediaItem)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _simpleMediaState.update { currentState ->
            currentState.copy(
                isPlaying = isPlaying
            )
        }
        GlobalScope.launch(Dispatchers.Main) {
            if (isPlaying) {
                startProgressUpdate()
            } else {
                stopProgressUpdate()
            }
        }
    }
}

