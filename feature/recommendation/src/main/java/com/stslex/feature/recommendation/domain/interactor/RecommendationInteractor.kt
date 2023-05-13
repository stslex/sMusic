package com.stslex.feature.recommendation.domain.interactor

import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow

interface RecommendationInteractor {

    val recommendations: Flow<YoutubePageDataModel?>

    fun getPlayerData(id: String): Flow<PlayerDataModel>
}