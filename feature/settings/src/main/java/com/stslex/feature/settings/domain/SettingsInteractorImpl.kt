package com.stslex.feature.settings.domain

import com.stslex.feature.settings.data.repository.SettingsDbModel
import com.stslex.feature.settings.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val repository: SettingsRepository
) : SettingsInteractor {

    override val settings: Flow<SettingsDbModel>
        get() = repository.settings

    override suspend fun updateSettings(settingsDbModel: SettingsDbModel) {
        repository.updateSettings(settingsDbModel)
    }
}