package com.stslex.core.player.data

import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow

interface MediaServiceRepository {

    fun getPlayerData(mediaItem: MediaItem): Flow<MediaItem>

    fun getPlayerData(id: String): Flow<PlayerDataModel>
}

