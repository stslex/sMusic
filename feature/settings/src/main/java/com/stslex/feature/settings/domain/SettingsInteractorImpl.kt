package com.stslex.feature.settings.domain

import com.stslex.core.datastore.SettingsDbModel
import com.stslex.feature.settings.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsInteractorImpl(
    private val repository: SettingsRepository
) : SettingsInteractor {

    override val settings: Flow<SettingsDbModel>
        get() = repository.settings

    override suspend fun updateSettings(settingsDbModel: SettingsDbModel) {
        repository.updateSettings(settingsDbModel)
    }
}