package com.stslex.core.network.data.model

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Thumbnail

sealed class Item(
    open val thumbnail: Thumbnail?,
    val key: String
) {

    data class SongItem(
        val info: Info<NavigationEndpoint.Endpoint.Watch>?,
        val authors: List<Info<NavigationEndpoint.Endpoint.Browse>>?,
        val album: Info<NavigationEndpoint.Endpoint.Browse>?,
        val durationText: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.videoId.orEmpty()
    )

    data class PlaylistItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val channel: Info<NavigationEndpoint.Endpoint.Browse>?,
        val songCount: Int?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )

    data class AlbumItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val authors: List<Info<NavigationEndpoint.Endpoint.Browse>>?,
        val year: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )

    data class ArtistItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val subscribersCountText: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )
}

