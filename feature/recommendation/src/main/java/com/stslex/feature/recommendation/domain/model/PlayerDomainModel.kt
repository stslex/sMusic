package com.stslex.feature.recommendation.domain.model

import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.player.PlayerDataModel

data class PlayerDomainModel(
    val songItem: ItemData.SongItem,
    val playerData: PlayerDataModel,
)