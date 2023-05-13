package com.stslex.feature.recommendation.data

import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

class RecommendationRepositoryImpl(
    private val client: YoutubeClient
) : RecommendationRepository {

    companion object {
        private const val TOP_CHARTS_ID = "J7p4bzqLvCw"
    }

    private var pageCache: YoutubePageDataModel? = null
    private var playerData: MutableMap<String, PlayerDataModel> = mutableMapOf()

    override val recommendations: Flow<YoutubePageDataModel>
        get() = pageCache
            ?.let(::flowOf)
            ?: client
                .makeNextRequest(TOP_CHARTS_ID)
                .onEach { pageDataModel ->
                    pageCache = pageDataModel
                }

    override fun getPlayerData(
        id: String
    ): Flow<PlayerDataModel> = playerData[id]
        ?.let(::flowOf)
        ?: client
            .getPlayerData(id)
            .onEach { data ->
                playerData[id] = data
            }
}