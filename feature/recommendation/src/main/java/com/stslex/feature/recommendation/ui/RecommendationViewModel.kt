package com.stslex.feature.recommendation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.model.PlayerEvent
import com.stslex.feature.recommendation.data.RecommendationRepository
import com.stslex.feature.recommendation.utils.mapToMediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext

class RecommendationViewModel(
    private val repository: RecommendationRepository,
    private val mediaController: MediaServiceController
) : ViewModel() {

    val recommendations: StateFlow<YoutubePageDataModel?>
        get() = repository.recommendations
            .flowOn(Dispatchers.IO)
            .onEach { recommendation ->
                loadMediaItemsToController(recommendation.songs)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                null
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadMediaItemsToController(songList: List<ItemData.SongItem>) {
        songList.forEachIndexed { index, songItem ->
            viewModelScope.launch(Dispatchers.IO) {
                repository
                    .getPlayerData(songItem.key)
                    .collect { playerData ->
                        withContext(Dispatchers.Main){
                            mediaController.addMediaItem(
                                index = index,
                                mediaItem = songItem.mapToMediaItem(playerData)
                            )
                        }

                    }
            }
        }
    }

    val currentPlayingMedia: StateFlow<MediaItem?>
        get() = mediaController.currentPlayingMedia

    fun play(id: String) {
        val event = PlayerEvent.PlayPauseCurrent(id)
        viewModelScope.launch {
            mediaController.onPlayerEvent(event)
        }
    }
}
