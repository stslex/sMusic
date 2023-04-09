package com.stslex.feature.favourite.di

import com.stslex.feature.favourite.ui.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val favouriteModule = module {
    viewModelOf(::FavouriteViewModel)
}