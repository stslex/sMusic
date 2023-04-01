package com.stslex.feature.settings.di

import com.stslex.feature.settings.data.repository.SettingsRepository
import com.stslex.feature.settings.data.repository.SettingsRepositoryImpl
import com.stslex.feature.settings.domain.SettingsInteractor
import com.stslex.feature.settings.domain.SettingsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingsModule {

    @Binds
    fun bindSettingsInteractor(interactor: SettingsInteractorImpl): SettingsInteractor

    @Binds
    fun bindsSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}