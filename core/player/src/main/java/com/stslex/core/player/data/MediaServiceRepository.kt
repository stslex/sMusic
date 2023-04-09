package com.stslex.core.player.data

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaServiceRepository {

    fun getPlayerData(mediaItem: MediaItem): Flow<MediaItem>

    fun getPlayerData(mediaItemList: List<MediaItem>): Flow<List<MediaItem>>
}

