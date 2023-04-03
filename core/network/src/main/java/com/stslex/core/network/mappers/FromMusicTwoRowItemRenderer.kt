package com.stslex.core.network.mappers

import com.stslex.core.network.model.data.Info
import com.stslex.core.network.model.data.Item
import com.stslex.core.network.model.response.browse.MusicTwoRowItemRenderer

internal fun mapToAlbumItem(renderer: MusicTwoRowItemRenderer): Item.AlbumItem? {
    return Item.AlbumItem(
        info = renderer
            .title
            ?.runs
            ?.firstOrNull()
            ?.let(::Info),
        authors = null,
        year = renderer
            .subtitle
            ?.runs
            ?.lastOrNull()
            ?.text,
        thumbnail = renderer
            .thumbnailRenderer
            ?.musicThumbnailRenderer
            ?.thumbnail
            ?.thumbnails
            ?.firstOrNull()
    ).takeIf { it.info?.endpoint?.browseId != null }
}

internal fun mapToArtisItem(renderer: MusicTwoRowItemRenderer): Item.ArtistItem? {
    return Item.ArtistItem(
        info = renderer
            .title
            ?.runs
            ?.firstOrNull()
            ?.let(::Info),
        subscribersCountText = renderer
            .subtitle
            ?.runs
            ?.firstOrNull()
            ?.text,
        thumbnail = renderer
            .thumbnailRenderer
            ?.musicThumbnailRenderer
            ?.thumbnail
            ?.thumbnails
            ?.firstOrNull()
    ).takeIf { it.info?.endpoint?.browseId != null }
}

internal fun mapToPlaylistItem(renderer: MusicTwoRowItemRenderer): Item.PlaylistItem? {
    return Item.PlaylistItem(
        info = renderer
            .title
            ?.runs
            ?.firstOrNull()
            ?.let(::Info),
        channel = renderer
            .subtitle
            ?.runs
            ?.getOrNull(2)
            ?.let(::Info),
        songCount = renderer
            .subtitle
            ?.runs
            ?.getOrNull(4)
            ?.text
            ?.split(' ')
            ?.firstOrNull()
            ?.toIntOrNull(),
        thumbnail = renderer
            .thumbnailRenderer
            ?.musicThumbnailRenderer
            ?.thumbnail
            ?.thumbnails
            ?.firstOrNull()
    ).takeIf { it.info?.endpoint?.browseId != null }
}