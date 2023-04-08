package com.stslex.core.network.model.data

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Thumbnail

internal sealed class Item(
    open val thumbnail: Thumbnail?,
    val key: String
) {

    internal data class SongItem(
        val info: Info<NavigationEndpoint.Endpoint.Watch>?,
        val authors: List<Info<NavigationEndpoint.Endpoint.Browse>>?,
        val album: Info<NavigationEndpoint.Endpoint.Browse>?,
        val durationText: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.videoId.orEmpty()
    )

    internal data class PlaylistItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val channel: Info<NavigationEndpoint.Endpoint.Browse>?,
        val songCount: Int?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )

    internal data class AlbumItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val authors: List<Info<NavigationEndpoint.Endpoint.Browse>>?,
        val year: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )

    internal data class ArtistItem(
        val info: Info<NavigationEndpoint.Endpoint.Browse>?,
        val subscribersCountText: String?,
        override val thumbnail: Thumbnail?
    ) : Item(
        thumbnail = thumbnail,
        key = info?.endpoint?.browseId.orEmpty()
    )
}
