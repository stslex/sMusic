package com.stslex.smusic.di

import com.stslex.smusic.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainActivityViewModel)
}
