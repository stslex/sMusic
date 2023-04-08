package com.stslex.core.network.model.data

internal data class YouTubePage(
    val songs: List<Item.SongItem>? = null,
    val playlists: List<Item.PlaylistItem>? = null,
    val albums: List<Item.AlbumItem>? = null,
    val artists: List<Item.ArtistItem>? = null,
)