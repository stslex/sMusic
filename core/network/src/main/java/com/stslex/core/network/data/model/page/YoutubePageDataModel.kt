package com.stslex.core.network.data.model.page

data class YoutubePageDataModel(
    val songs: List<ItemData.SongItem> = emptyList(),
    val playlists: List<ItemData.PlaylistItem> = emptyList(),
    val albums: List<ItemData.AlbumItem> = emptyList(),
    val artists: List<ItemData.ArtistItem> = emptyList(),
)
