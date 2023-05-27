package com.stslex.feature.player.di

import com.stslex.feature.player.ui.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featurePlayerModule = module {
    viewModelOf(::PlayerViewModel)
}