package com.stslex.core.player.controller

import androidx.media3.common.MediaItem
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.flow.StateFlow

interface MediaServiceController {

    val simpleMediaState: StateFlow<SimpleMediaState>

    val allMediaItems: StateFlow<List<MediaItem>>

    val currentPlayingMedia: StateFlow<MediaItem?>

    suspend fun onPlayerEvent(playerEvent: PlayerEvent)

    fun addMediaItem(index: Int, mediaItem: MediaItem)

    fun addMediaItems(items: List<MediaItem>)
}