package com.stslex.feature.settings.data.repository

import com.stslex.core.datastore.SettingsDataStore
import com.stslex.core.datastore.SettingsDbModel
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override val settings: Flow<SettingsDbModel>
        get() = dataStore.settings

    override suspend fun updateSettings(settingsDbModel: SettingsDbModel) {
        dataStore.updateSettings(settingsDbModel)
    }
}
