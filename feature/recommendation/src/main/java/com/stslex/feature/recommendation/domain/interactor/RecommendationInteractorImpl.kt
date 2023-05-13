package com.stslex.feature.recommendation.domain.interactor

import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import com.stslex.feature.recommendation.data.RecommendationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class RecommendationInteractorImpl(
    private val repository: RecommendationRepository,
) : RecommendationInteractor {


    override val recommendations: Flow<YoutubePageDataModel?>
        get() = repository.recommendations
            .flowOn(Dispatchers.IO)

    override fun getPlayerData(
        id: String
    ): Flow<PlayerDataModel> = repository.getPlayerData(id)
}

// TODO put in project core
inline fun <reified T> List<Flow<T>>.asFlowList(): Flow<List<T>> =
    combine(*toTypedArray()) { array -> array.toList() }