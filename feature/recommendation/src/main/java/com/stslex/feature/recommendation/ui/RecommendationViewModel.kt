package com.stslex.feature.recommendation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.feature.recommendation.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendationViewModel(
    private val repository: HomeRepository,
    private val mediaController: MediaServiceController
) : ViewModel() {

    val recommendations: StateFlow<YoutubePageDataModel?>
        get() = repository.recommendations
            .flowOn(Dispatchers.IO)
            .onEach { recommendation ->
                mediaController.addMediaItems(recommendation.songs)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                null
            )

    val currentPlayingMedia: StateFlow<MediaItem?>
        get() = mediaController.currentPlayingMedia

    fun play(id: String) {
        val event = PlayerEvent.PlayPauseCurrent(id)
        viewModelScope.launch {
            mediaController.onPlayerEvent(event)
        }
    }
}
