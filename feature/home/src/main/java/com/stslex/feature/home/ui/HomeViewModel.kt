package com.stslex.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import com.stslex.core.network.model.Value
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

class HomeViewModel(
    private val repository: HomeRepository
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
}
