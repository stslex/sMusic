package com.stslex.feature.settings.di

import com.stslex.feature.settings.data.repository.SettingsRepository
import com.stslex.feature.settings.data.repository.SettingsRepositoryImpl
import com.stslex.feature.settings.domain.SettingsInteractor
import com.stslex.feature.settings.domain.SettingsInteractorImpl
import com.stslex.feature.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    factoryOf(::SettingsInteractorImpl) { bind<SettingsInteractor>() }
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
}
