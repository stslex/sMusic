package com.stslex.feature.settings.data.repository

import com.stslex.core.datastore.SettingsDbModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settings: Flow<SettingsDbModel>

    suspend fun updateSettings(settingsDbModel: SettingsDbModel)
}