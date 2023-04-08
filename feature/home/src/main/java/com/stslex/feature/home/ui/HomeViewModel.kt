package com.stslex.feature.home.ui

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import com.stslex.core.network.model.Value
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.player.service.MediaServiceHandler
import com.stslex.core.ui.extensions.toPx
import com.stslex.feature.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    private val mediaHandler: MediaServiceHandler
) : ViewModel() {

    val recommendations: StateFlow<YoutubePageDataModel>
        get() = repository.recommendations
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                YoutubePageDataModel()
            )

    private val _playerUrl: MutableStateFlow<Value<PlayerDataModel>> =
        MutableStateFlow(Value.Loading)

    val playerUrl: StateFlow<Value<PlayerDataModel>>
        get() = _playerUrl

    val mediaState: StateFlow<SimpleMediaState> = mediaHandler.simpleMediaState

    fun getPlayerData(id: String) {
        _playerUrl.tryEmit(Value.Loading)
        repository.getPlayerData(id = id)
            .flowOn(Dispatchers.IO)
            .catch { error ->
                _playerUrl.emit(Value.Error(error))
            }
            .onEach { item ->
                _playerUrl.emit(Value.Content(item))
            }
            .launchIn(viewModelScope)
    }

    fun play(mediaItem: MediaItem) {
        viewModelScope.launch {
            mediaHandler.addMediaItem(mediaItem)
            mediaHandler.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }

    fun stop() {
        viewModelScope.launch {
            mediaHandler.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }
}

val ItemData.SongItem.asMediaItem: (Uri, Int) -> MediaItem
    @OptIn(UnstableApi::class)
    get() = { uri, size ->
        MediaItem.Builder()
            .setMediaId(key)
            .setUri(uri)
            .setCustomCacheKey(key)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(info.name)
                    .setArtist(authors.joinToString("") { it.name })
                    .setAlbumTitle(album.name)
                    .setArtworkUri(
                        thumbnail.size(size).toUri()
                    )
                    .setExtras(
                        bundleOf(
                            "albumId" to album.browseId,
                            "durationText" to durationText,
                            "artistNames" to authors.map { it.name },
                            "artistIds" to authors.map { it.browseId },
                        )
                    )
                    .build()
            )
            .build()
    }
