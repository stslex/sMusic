package com.stslex.core.player.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.flow.StateFlow

interface MediaServiceHandler : Player.Listener {

    val simpleMediaState: StateFlow<SimpleMediaState>

    fun addMediaItem(mediaItem: MediaItem)

    fun addMediaItemList(mediaItemList: List<MediaItem>)

    suspend fun onPlayerEvent(playerEvent: PlayerEvent)
}