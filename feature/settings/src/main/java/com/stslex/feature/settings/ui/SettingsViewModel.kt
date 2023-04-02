package com.stslex.feature.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.feature.settings.data.repository.SettingsDbModel
import com.stslex.feature.settings.domain.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
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