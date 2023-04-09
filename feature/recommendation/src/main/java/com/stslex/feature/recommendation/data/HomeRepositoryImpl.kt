package com.stslex.feature.recommendation.data

import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val client: YoutubeClient
) : HomeRepository {

    companion object {
        private const val TOP_CHARTS_ID = "J7p4bzqLvCw"
    }

    override val recommendations: Flow<YoutubePageDataModel>
        get() = client.makeNextRequest(TOP_CHARTS_ID)

    override fun getPlayerData(id: String): Flow<PlayerDataModel> =
        client.getPlayerData(id)
}