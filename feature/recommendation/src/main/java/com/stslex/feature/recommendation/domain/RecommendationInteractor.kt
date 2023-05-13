package com.stslex.feature.recommendation.domain

import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.feature.recommendation.domain.model.PlayerDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RecommendationInteractor {

    val playerData: StateFlow<List<PlayerDomainModel>>

    val recommendations: Flow<YoutubePageDataModel?>
}