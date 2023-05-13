package com.stslex.feature.recommendation.domain

import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.feature.recommendation.data.RecommendationRepository
import com.stslex.feature.recommendation.domain.model.PlayerDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class RecommendationInteractorImpl(
    private val repository: RecommendationRepository,
) : RecommendationInteractor {

    private val _playerData = MutableStateFlow<List<PlayerDomainModel>>(emptyList())
    override val playerData: StateFlow<List<PlayerDomainModel>>
        get() = _playerData.asStateFlow()


    override val recommendations: Flow<YoutubePageDataModel?>
        get() = repository.recommendations
            .onEach { recommendation ->
                loadMediaItemsToController(recommendation.songs)
            }
            .flowOn(Dispatchers.IO)

    private fun getPlayerDate(
        songItem: ItemData.SongItem
    ): Flow<PlayerDomainModel> = repository
        .getPlayerData(songItem.key)
        .map { playerData ->
            PlayerDomainModel(
                songItem = songItem,
                playerData = playerData,
            )
        }

    private suspend fun loadMediaItemsToController(
        songList: List<ItemData.SongItem>
    ) {
        songList
            .map(::getPlayerDate)
            .asFlowList()
            .collect(_playerData::emit)
    }
}

// TODO put in project core
inline fun <reified T> List<Flow<T>>.asFlowList(): Flow<List<T>> =
    combine(*toTypedArray()) { array -> array.toList() }