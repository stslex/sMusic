package com.stslex.core.player.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerState
import com.stslex.core.player.model.SimpleMediaState
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
    private val player: ExoPlayer,
) : MediaServiceController {

    private val _simpleMediaState = MutableStateFlow(SimpleMediaState())
    override val simpleMediaState: StateFlow<SimpleMediaState>
        get() = _simpleMediaState.asStateFlow()

    private val _currentPlayingMedia = MutableStateFlow<MediaItem?>(null)
    override val currentPlayingMedia: StateFlow<MediaItem?>
        get() = _currentPlayingMedia.asStateFlow()

    private val mediaCache = mutableMapOf<String, Int>()

    private var job: Job

    init {
        player.addListener(this)
        job = Job()
    }

    override fun addMediaItem(index: Int, mediaItem: MediaItem) {
        mediaCache[mediaItem.mediaId] = index
        player.addMediaItem(index, mediaItem)
    }

    override fun addMediaItems(items: List<MediaItem>) {
        items.forEachIndexed(::addMediaItem)
    }

    override suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            is PlayerEvent.Backward -> player.seekBack()
            is PlayerEvent.Forward -> player.seekForward()
            is PlayerEvent.Next -> player.seekToNext()
            is PlayerEvent.Previous -> player.seekToPrevious()
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
                player.playWhenReady = player.playWhenReady.not()
            }

            is PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())

            is PlayerEvent.PlayPauseCurrent -> {
                mediaCache[playerEvent.id]?.let { index ->
                    player.seekTo(index, 0)
                    player.prepare()
                    player.play()
                }
            }
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

            Player.STATE_ENDED -> {
                // TODO()
            }

            Player.STATE_IDLE -> {
                // TODO()
            }
        }
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

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        _currentPlayingMedia.tryEmit(player.currentMediaItem)
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
}

