package com.stslex.feature.settings.domain

import com.stslex.feature.settings.data.repository.SettingsDbModel
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {

    val settings: Flow<SettingsDbModel>

    suspend fun updateSettings(settingsDbModel: SettingsDbModel)
}