package com.stslex.core.player.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaServiceControllerImpl(
    private val player: ExoPlayer,
) : MediaServiceController {

    private val _simpleMediaState = MutableStateFlow<SimpleMediaState>(SimpleMediaState.Initial)
    override val simpleMediaState: StateFlow<SimpleMediaState>
        get() = _simpleMediaState.asStateFlow()

    private val _playerPlayingState = MutableStateFlow(PlayerPlayingState.PAUSE)
    override val playerPlayingState: StateFlow<PlayerPlayingState>
        get() = _playerPlayingState.asStateFlow()

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

    override suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.Backward -> player.seekBack()
            PlayerEvent.Forward -> player.seekForward()
            PlayerEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                    stopProgressUpdate()
                } else {
                    player.play()
                    _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }

            PlayerEvent.ResumePause -> {
                if (simpleMediaState.value == SimpleMediaState.Initial) {
                    currentPlayingMedia.value?.let {
                        player.addMediaItem(it)
                        player.prepare()
                    }
                }
                player.playWhenReady = player.playWhenReady.not()
            }

            PlayerEvent.Stop -> stopProgressUpdate()
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
            ExoPlayer.STATE_BUFFERING -> _simpleMediaState.value =
                SimpleMediaState.Buffering(player.currentPosition)

            ExoPlayer.STATE_READY -> _simpleMediaState.value =
                SimpleMediaState.Ready(player.duration)

            Player.STATE_ENDED -> {
                // TODO()
            }

            Player.STATE_IDLE -> {
                // TODO()
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = isPlaying)
        if (isPlaying) {
            _playerPlayingState.value = PlayerPlayingState.PLAY
            // TODO
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            _playerPlayingState.value = PlayerPlayingState.PAUSE
            stopProgressUpdate()
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        _currentPlayingMedia.tryEmit(player.currentMediaItem)
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _simpleMediaState.value = SimpleMediaState.Progress(
                progress = player.currentPosition,
                duration = player.duration
            )
        }
    }

    private fun stopProgressUpdate() {
        job.cancel()
        _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = false)
    }
}

