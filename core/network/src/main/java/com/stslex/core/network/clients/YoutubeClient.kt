package com.stslex.core.network.clients

import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow

interface YoutubeClient {

    fun makeNextRequest(id: String): Flow<YoutubePageDataModel>

    fun getPlayerData(id: String): Flow<PlayerDataModel>

    suspend fun getPlayerDataRow(id: String): PlayerDataModel?
}

