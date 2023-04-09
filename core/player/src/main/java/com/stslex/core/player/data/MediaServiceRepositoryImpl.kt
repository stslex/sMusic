package com.stslex.core.player.data

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MediaServiceRepositoryImpl(
    private val client: YoutubeClient
) : MediaServiceRepository {

    override fun getPlayerData(mediaItem: MediaItem): Flow<MediaItem> = client
        .getPlayerData(mediaItem.mediaId)
        .map { value: PlayerDataModel ->
            mediaItem.mapToPlayer(value)
        }

    override fun getPlayerData(mediaItemList: List<MediaItem>): Flow<List<MediaItem>> = flow {
        val result = mediaItemList.mapNotNull { mediaItem ->
            client.getPlayerDataRow(mediaItem.mediaId)?.let {
                mediaItem.mapToPlayer(it)
            }
        }
        emit(result)
    }

    @OptIn(UnstableApi::class)
    private fun MediaItem.mapToPlayer(
        dataModel: PlayerDataModel
    ): MediaItem = MediaItem.Builder()
        .setMediaId(mediaId)
        .setUri(
            dataModel.streamingData
                .highestQualityFormat
                ?.url
                .orEmpty()
                .toUri()
        )
        .setCustomCacheKey(mediaId)
        .setMediaMetadata(mediaMetadata)
        .build()
}