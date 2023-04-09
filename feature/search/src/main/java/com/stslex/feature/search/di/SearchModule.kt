package com.stslex.feature.search.di

import com.stslex.feature.search.ui.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchScreenViewModel)
}