package com.stslex.feature.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.core.datastore.SettingsDbModel
import com.stslex.feature.settings.domain.SettingsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val interactor: SettingsInteractor
) : ViewModel() {

    val settings: StateFlow<SettingsDbModel>
        get() = interactor.settings
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SettingsDbModel()
            )

    fun updateSettings(currentSettings: SettingsDbModel) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.updateSettings(currentSettings)
        }
    }
}