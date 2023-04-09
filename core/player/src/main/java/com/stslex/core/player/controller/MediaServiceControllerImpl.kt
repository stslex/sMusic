package com.stslex.core.player.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.player.data.MediaServiceRepository
import com.stslex.core.player.data.mapToMediaItem
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MediaServiceControllerImpl(
    private val player: ExoPlayer,
    private val mediaServiceRepository: MediaServiceRepository
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

    private val MediaItem.networkItem: Flow<MediaItem>
        get() = mediaServiceRepository
            .getPlayerData(this)
            .flowOn(Dispatchers.IO)

    private val mediaCache = mutableMapOf<String, MediaItem>()

    private var job: Job

    init {
        player.addListener(this)
        job = Job()
    }

    override suspend fun addMediaItem(mediaItem: MediaItem) {
        mediaItem.networkItem.collect { item ->
            player.setMediaItem(item)
            player.prepare()
        }
    }

    override suspend fun addMediaItemList(mediaItemList: List<MediaItem>) {
        player.setMediaItems(mediaItemList)
        player.prepare()
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
                player.playWhenReady = player.playWhenReady.not()
            }

            PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())

            is PlayerEvent.PlayPauseCurrent -> {
                val mediaItem = mediaCache[playerEvent.songItem.key]
                if (mediaItem == null) {
                    playNewMediaItem(
                        songItem = playerEvent.songItem,
                        index = playerEvent.index
                    )
                } else {
                    player.seekTo(playerEvent.index, 0)
                    player.prepare()
                    player.play()
                }
            }
        }
    }

    private suspend fun playNewMediaItem(
        songItem: ItemData.SongItem,
        index: Int
    ) {
        mediaServiceRepository
            .getPlayerData(songItem.key)
            .flowOn(Dispatchers.IO)
            .collect { playerDataModel ->
                val currentMedia = songItem.mapToMediaItem(playerDataModel)
                mediaCache[songItem.key] = currentMedia
                player.addMediaItem(index, currentMedia)
                player.seekTo(index, 0)
                player.prepare()
                player.play()
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
        _currentPlayingMedia.tryEmit(mediaItem)
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

    override suspend fun addMediaItems(songItemList: List<ItemData.SongItem>) {
        songItemList.forEachIndexed { index, songItem ->
            if (mediaCache.containsKey(songItem.key).not()) {
                mediaServiceRepository
                    .getPlayerData(songItem.key)
                    .flowOn(Dispatchers.IO)
                    .collect { playerDataModel ->
                        val mediaItem = songItem.mapToMediaItem(playerDataModel)
                        mediaCache[songItem.key] = mediaItem
                        player.addMediaItem(index, mediaItem)
                    }
            }
        }
    }
}

