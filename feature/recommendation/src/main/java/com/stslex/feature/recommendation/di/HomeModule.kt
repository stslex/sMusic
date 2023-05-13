package com.stslex.feature.recommendation.di

import com.stslex.feature.recommendation.data.RecommendationRepository
import com.stslex.feature.recommendation.data.RecommendationRepositoryImpl
import com.stslex.feature.recommendation.domain.RecommendationInteractor
import com.stslex.feature.recommendation.domain.RecommendationInteractorImpl
import com.stslex.feature.recommendation.ui.RecommendationViewModel
import com.stslex.feature.recommendation.ui.mapper.MediaMapper
import com.stslex.feature.recommendation.ui.mapper.MediaMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::RecommendationViewModel)
    singleOf(::RecommendationRepositoryImpl) { bind<RecommendationRepository>() }
    factoryOf(::RecommendationInteractorImpl) { bind<RecommendationInteractor>() }
    factoryOf(::MediaMapperImpl) { bind<MediaMapper>() }
}