package com.stslex.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.feature.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    private val mediaController: MediaServiceController
) : ViewModel() {

    val recommendations: StateFlow<YoutubePageDataModel>
        get() = repository.recommendations
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                YoutubePageDataModel()
            )

    val currentPlayingMedia: StateFlow<MediaItem?>
        get() = mediaController.currentPlayingMedia

    fun play(mediaItem: MediaItem) {
        viewModelScope.launch {
            mediaController.addMediaItem(mediaItem)
            mediaController.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }

    fun stop() {
        viewModelScope.launch {
            mediaController.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }
}
