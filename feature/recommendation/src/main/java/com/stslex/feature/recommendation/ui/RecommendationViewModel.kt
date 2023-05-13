package com.stslex.feature.recommendation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.feature.recommendation.domain.interactor.RecommendationInteractor
import com.stslex.feature.recommendation.domain.interactor.asFlowList
import com.stslex.feature.recommendation.ui.mapper.MediaMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
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
        interactor.recommendations
            .flowOn(Dispatchers.IO)
            .onEach { recommendations ->
                recommendations?.songs
                    .orEmpty()
                    .map { songItem ->
                        interactor
                            .getPlayerData(songItem.key)
                            .map { playerData ->
                                mapper.map(songItem, playerData)
                            }
                    }
                    .asFlowList()
                    .flowOn(Dispatchers.IO)
                    .onEach(mediaController::addMediaItems)
                    .launchIn(viewModelScope)
            }
            .launchIn(viewModelScope)
    }

    val recommendations: StateFlow<List<ItemData.SongItem>>
        get() = interactor.recommendations
            .map { recommendations ->
                recommendations?.songs.orEmpty()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
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
