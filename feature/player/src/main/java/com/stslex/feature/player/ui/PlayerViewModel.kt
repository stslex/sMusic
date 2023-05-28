package com.stslex.feature.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mediaController: MediaServiceController
) : ViewModel() {

    val currentMediaItem: StateFlow<MediaItem?>
        get() = mediaController.currentPlayingMedia

    val simpleMediaState: StateFlow<SimpleMediaState>
        get() = mediaController.simpleMediaState

    val allMediaItems: StateFlow<List<MediaItem>>
        get() = mediaController.allMediaItems

    fun onPlayerClick(event: PlayerEvent) {
        viewModelScope.launch {
            mediaController.onPlayerEvent(event)
        }
    }
}