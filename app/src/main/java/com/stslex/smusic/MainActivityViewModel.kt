package com.stslex.smusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.core.datastore.SettingsDataStore
import com.stslex.core.datastore.SettingsDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val settings: StateFlow<SettingsDbModel>
        get() = settingsDataStore.settings
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SettingsDbModel()
            )
}