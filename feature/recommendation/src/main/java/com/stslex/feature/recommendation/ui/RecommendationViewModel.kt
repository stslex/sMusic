package com.stslex.feature.recommendation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.feature.recommendation.domain.RecommendationInteractor
import com.stslex.feature.recommendation.ui.mapper.MediaMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendationViewModel(
    private val interactor: RecommendationInteractor,
    private val mediaController: MediaServiceController,
    private val mapper: MediaMapper
) : ViewModel() {

    init {
        interactor.playerData
            .map(mapper::map)
            .onEach(mediaController::addMediaItems)
            .launchIn(viewModelScope)
    }

    val recommendations: StateFlow<YoutubePageDataModel?>
        get() = interactor.recommendations
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = null
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
