package com.stslex.feature.recommendation.ui.mapper

import android.net.Uri
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.player.PlayerDataModel

class MediaMapperImpl : MediaMapper {

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun map(
        item: ItemData.SongItem,
        playerData: PlayerDataModel,
        size: Int
    ): MediaItem = MediaItem.Builder()
        .setMediaId(item.key)
        .setUri(playerData.uri)
        .setCustomCacheKey(item.key)
        .setMediaMetadata(item.asMediaMetadata(size))
        .build()

    private val PlayerDataModel.uri: Uri
        get() = streamingData
            .highestQualityFormat
            ?.url
            .orEmpty()
            .toUri()

    private val ItemData.SongItem.asMediaMetadata: (size: Int) -> MediaMetadata
        get() = { size ->
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
        }
}