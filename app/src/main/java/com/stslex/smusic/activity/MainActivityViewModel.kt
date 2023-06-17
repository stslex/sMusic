package com.stslex.smusic.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.datastore.SettingsDataStore
import com.stslex.core.datastore.SettingsDbModel
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val settingsDataStore: SettingsDataStore,
    private val mediaController: MediaServiceController
) : ViewModel() {

    val settings: StateFlow<SettingsDbModel>
        get() = settingsDataStore.settings
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SettingsDbModel()
            )

    val currentMediaItem: StateFlow<MediaItem?>
        get() = mediaController.currentPlayingMedia

    val simpleMediaState: StateFlow<SimpleMediaState>
        get() = mediaController.simpleMediaState

    fun onPlayerClick(event: PlayerEvent) {
        viewModelScope.launch {
            mediaController.onPlayerEvent(event)
        }
    }
}