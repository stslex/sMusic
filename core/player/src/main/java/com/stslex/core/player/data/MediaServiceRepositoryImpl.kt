package com.stslex.core.player.data

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.player.PlayerDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MediaServiceRepositoryImpl(
    private val client: YoutubeClient
) : MediaServiceRepository {

    override fun getPlayerData(mediaItem: MediaItem): Flow<MediaItem> = client
        .getPlayerData(mediaItem.mediaId)
        .map { value: PlayerDataModel ->
            mediaItem.mapToPlayer(value)
        }

    override fun getPlayerData(id: String): Flow<PlayerDataModel> = client.getPlayerData(id)
}

@OptIn(UnstableApi::class)
fun MediaItem.mapToPlayer(
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

@OptIn(UnstableApi::class)
fun ItemData.SongItem.mapToMediaItem(
    playerDataModel: PlayerDataModel
): MediaItem = MediaItem.Builder()
    .setMediaId(key)
    .setUri(
        playerDataModel.streamingData
            .highestQualityFormat
            ?.url
            .orEmpty()
            .toUri()
    )
    .setCustomCacheKey(key)
    .setMediaMetadata(asMediaMetadata)
    .build()

val ItemData.SongItem.asMediaMetadata: MediaMetadata
    get() = MediaMetadata.Builder()
        .setTitle(info.name)
        .setArtist(authors.joinToString("") { it.name })
        .setAlbumTitle(album.name)
        .setArtworkUri(
            thumbnail.size(300).toUri()
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