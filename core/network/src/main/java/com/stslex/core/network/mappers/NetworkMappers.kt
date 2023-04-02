package com.stslex.core.network.mappers

import com.stslex.core.network.data.model.Info
import com.stslex.core.network.data.model.Item
import com.stslex.core.network.data.model.YouTubePage
import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Runs
import com.stslex.core.network.model.response.browse.MusicCarouselShelfRenderer
import com.stslex.core.network.model.response.browse.MusicResponsiveListItemRenderer
import com.stslex.core.network.model.response.browse.SectionListRenderer

fun SectionListRenderer?.mapYouTubePage() = YouTubePage(
    songs = mapSong(),
    playlists = mapPlayList(),
    albums = mapAlbums(),
    artists = mapArtists(),
)

fun SectionListRenderer?.mapSong(): List<Item.SongItem>? = this
    ?.findSectionByTitle("You might also like")
    ?.mapToContents()
    ?.mapNotNull(MusicCarouselShelfRenderer.Content::musicResponsiveListItemRenderer)
    ?.mapNotNull(::mapToSongItem)

fun SectionListRenderer?.mapPlayList(): List<Item.PlaylistItem>? = this
    ?.findSectionByTitle("Recommended playlists")
    ?.mapToContents()
    ?.mapNotNull(MusicCarouselShelfRenderer.Content::musicTwoRowItemRenderer)
    ?.mapNotNull(::mapToPlaylistItem)
    ?.sortedByDescending { it.channel?.name == "YouTube Music" }

fun SectionListRenderer?.mapAlbums(): List<Item.AlbumItem>? = this
    ?.findSectionByStrapline("MORE FROM")
    ?.mapToContents()
    ?.mapNotNull(MusicCarouselShelfRenderer.Content::musicTwoRowItemRenderer)
    ?.mapNotNull(::mapToAlbumItem)

fun SectionListRenderer?.mapArtists(): List<Item.ArtistItem>? = this
    ?.findSectionByTitle("Similar artists")
    ?.mapToContents()
    ?.mapNotNull(MusicCarouselShelfRenderer.Content::musicTwoRowItemRenderer)
    ?.mapNotNull(::mapToArtisItem)

fun SectionListRenderer.Content.mapToContents(): List<MusicCarouselShelfRenderer.Content>? = this
    .musicCarouselShelfRenderer
    ?.contents

fun mapToSongItem(renderer: MusicResponsiveListItemRenderer): Item.SongItem? {
    return Item.SongItem(
        info = renderer
            .flexColumns
            .getOrNull(0)
            ?.musicResponsiveListItemFlexColumnRenderer
            ?.text
            ?.runs
            ?.getOrNull(0)
            ?.let(::Info),
        authors = renderer
            .flexColumns
            .getOrNull(1)
            ?.musicResponsiveListItemFlexColumnRenderer
            ?.text
            ?.runs
            ?.map<Runs.Run, Info<NavigationEndpoint.Endpoint.Browse>>(::Info)
            ?.takeIf(List<Any>::isNotEmpty),
        durationText = renderer
            .fixedColumns
            ?.getOrNull(0)
            ?.musicResponsiveListItemFlexColumnRenderer
            ?.text
            ?.runs
            ?.getOrNull(0)
            ?.text,
        album = renderer
            .flexColumns
            .getOrNull(2)
            ?.musicResponsiveListItemFlexColumnRenderer
            ?.text
            ?.runs
            ?.firstOrNull()
            ?.let(::Info),
        thumbnail = renderer
            .thumbnail
            ?.musicThumbnailRenderer
            ?.thumbnail
            ?.thumbnails
            ?.firstOrNull()
    ).takeIf { it.info?.endpoint?.videoId != null }
}
