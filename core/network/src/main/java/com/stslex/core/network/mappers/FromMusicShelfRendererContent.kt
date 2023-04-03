package com.stslex.core.network.mappers

import com.stslex.core.network.model.data.Info
import com.stslex.core.network.model.data.Item
import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.browse.MusicShelfRenderer

internal fun mapToSong(content: MusicShelfRenderer.Content): Item.SongItem? {
    val (mainRuns, otherRuns) = content.runs

    /** Possible configurations:
    "song" • author(s) • album • duration
    "song" • author(s) • duration
    author(s) • album • duration
    author(s) • duration*/

    val album: Info<NavigationEndpoint.Endpoint.Browse>? = otherRuns
        .getOrNull(otherRuns.lastIndex - 1)
        ?.firstOrNull()
        ?.takeIf { run ->
            run
                .navigationEndpoint
                ?.browseEndpoint
                ?.type == "MUSIC_PAGE_TYPE_ALBUM"
        }
        ?.let(::Info)

    return Item.SongItem(
        info = mainRuns
            .firstOrNull()
            ?.let(::Info),
        authors = otherRuns
            .getOrNull(otherRuns.lastIndex - if (album == null) 1 else 2)
            ?.map(::Info),
        album = album,
        durationText = otherRuns
            .lastOrNull()
            ?.firstOrNull()?.text,
        thumbnail = content
            .thumbnail
    ).takeIf { it.info?.endpoint?.videoId != null }
}

internal fun mapToAlbum(content: MusicShelfRenderer.Content): Item.AlbumItem? {
    val (mainRuns, otherRuns) = content.runs

    return Item.AlbumItem(
        info = Info(
            name = mainRuns.firstOrNull()?.text,
            endpoint = content
                .musicResponsiveListItemRenderer
                ?.navigationEndpoint
                ?.browseEndpoint
        ),
        authors = otherRuns.getOrNull(otherRuns.lastIndex - 1)?.map(::Info),
        year = otherRuns
            .getOrNull(otherRuns.lastIndex)
            ?.firstOrNull()
            ?.text,
        thumbnail = content
            .thumbnail
    ).takeIf { it.info?.endpoint?.browseId != null }
}

internal fun mapToArtist(content: MusicShelfRenderer.Content): Item.ArtistItem? {
    val (mainRuns, otherRuns) = content.runs

    return Item.ArtistItem(
        info = Info(
            name = mainRuns
                .firstOrNull()
                ?.text,
            endpoint = content
                .musicResponsiveListItemRenderer
                ?.navigationEndpoint
                ?.browseEndpoint
        ),
        subscribersCountText = otherRuns
            .lastOrNull()
            ?.last()
            ?.text,
        thumbnail = content
            .thumbnail
    ).takeIf { it.info?.endpoint?.browseId != null }
}

internal fun mapToPlaylist(content: MusicShelfRenderer.Content): Item.PlaylistItem? {
    val (mainRuns, otherRuns) = content.runs

    return Item.PlaylistItem(
        info = Info(
            name = mainRuns
                .firstOrNull()
                ?.text,
            endpoint = content
                .musicResponsiveListItemRenderer
                ?.navigationEndpoint
                ?.browseEndpoint
        ),
        channel = otherRuns
            .firstOrNull()
            ?.firstOrNull()
            ?.let(::Info),
        songCount = otherRuns
            .lastOrNull()
            ?.firstOrNull()
            ?.text
            ?.split(' ')
            ?.firstOrNull()
            ?.toIntOrNull(),
        thumbnail = content
            .thumbnail
    ).takeIf { it.info?.endpoint?.browseId != null }
}