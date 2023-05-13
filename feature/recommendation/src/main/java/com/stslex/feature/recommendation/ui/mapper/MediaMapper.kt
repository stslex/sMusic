package com.stslex.feature.recommendation.ui.mapper

import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.player.PlayerDataModel

interface MediaMapper {

    fun map(
        item: ItemData.SongItem,
        playerData: PlayerDataModel
    ): MediaItem
}