package com.stslex.feature.home.data

import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    val recommendations: Flow<YoutubePageDataModel>

    fun getPlayerData(id: String): Flow<PlayerDataModel>
}