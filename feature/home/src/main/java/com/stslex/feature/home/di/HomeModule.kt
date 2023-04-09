package com.stslex.feature.home.di

import com.stslex.core.player.data.MediaServiceRepository
import com.stslex.core.player.data.MediaServiceRepositoryImpl
import com.stslex.feature.home.data.HomeRepository
import com.stslex.feature.home.data.HomeRepositoryImpl
import com.stslex.feature.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::HomeRepositoryImpl) { bind<HomeRepository>() }
    singleOf(::MediaServiceRepositoryImpl) { bind<MediaServiceRepository>() }
}