package com.stslex.feature.home.utils

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.stslex.core.network.data.model.page.ItemData

val ItemData.SongItem.asMediaItem: MediaItem
    @OptIn(UnstableApi::class)
    get() = MediaItem.Builder()
        .setMediaId(key)
        .setUri(key)
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
