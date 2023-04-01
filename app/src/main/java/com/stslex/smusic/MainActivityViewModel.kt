package com.stslex.smusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.feature.settings.data.repository.SettingsDbModel
import com.stslex.feature.settings.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<SettingsDbModel>
        get() = repository.settings
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SettingsDbModel()
            )
}