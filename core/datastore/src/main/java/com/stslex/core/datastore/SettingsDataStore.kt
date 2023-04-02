package com.stslex.core.datastore

import kotlinx.coroutines.flow.Flow

interface SettingsDataStore {

    val settings: Flow<SettingsDbModel>

    suspend fun updateSettings(settings: SettingsDbModel)
}