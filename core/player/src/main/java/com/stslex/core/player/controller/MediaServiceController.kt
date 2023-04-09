package com.stslex.core.player.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.flow.StateFlow

interface MediaServiceController : Player.Listener {

    val simpleMediaState: StateFlow<SimpleMediaState>

    val currentPlayingMedia: StateFlow<MediaItem?>

    val playerPlayingState: StateFlow<PlayerPlayingState>

    suspend fun addMediaItem(mediaItem: MediaItem)

    suspend fun addMediaItemList(mediaItemList: List<MediaItem>)

    suspend fun onPlayerEvent(playerEvent: PlayerEvent)
}