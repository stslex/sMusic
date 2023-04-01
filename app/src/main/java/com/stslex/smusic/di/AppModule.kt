package com.stslex.smusic.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun provideSettingsDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore
}