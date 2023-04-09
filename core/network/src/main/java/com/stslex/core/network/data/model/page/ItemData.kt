package com.stslex.core.network.data.model.page

sealed class ItemData(
    open val thumbnail: ThumbnailDataModel,
    val key: String
) {

    data class SongItem(
        val info: WatchDataModel = WatchDataModel(),
        val authors: List<BrowseDataModel> = emptyList(),
        val album: BrowseDataModel = BrowseDataModel(),
        val durationText: String = "",
        override val thumbnail: ThumbnailDataModel = ThumbnailDataModel(),
    ) : ItemData(
        thumbnail = thumbnail,
        key = info.videoId
    )

    data class PlaylistItem(
        val info: BrowseDataModel,
        val channel: BrowseDataModel,
        val songCount: Int,
        override val thumbnail: ThumbnailDataModel,
    ) : ItemData(
        thumbnail = thumbnail,
        key = info.browseId
    )

    data class AlbumItem(
        val info: BrowseDataModel,
        val authors: List<BrowseDataModel>,
        val year: String,
        override val thumbnail: ThumbnailDataModel
    ) : ItemData(
        thumbnail = thumbnail,
        key = info.browseId
    )

    data class ArtistItem(
        val info: BrowseDataModel,
        val subscribersCountText: String,
        override val thumbnail: ThumbnailDataModel
    ) : ItemData(
        thumbnail = thumbnail,
        key = info.browseId
    )
}