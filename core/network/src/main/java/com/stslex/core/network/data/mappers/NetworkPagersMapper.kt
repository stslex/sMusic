package com.stslex.core.network.data.mappers

import com.stslex.core.network.data.model.page.BrowseDataModel
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.data.model.page.ThumbnailDataModel
import com.stslex.core.network.data.model.page.WatchDataModel
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.model.data.Info
import com.stslex.core.network.model.data.Item
import com.stslex.core.network.model.data.YouTubePage
import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Thumbnail

internal fun YouTubePage.mapToData(): YoutubePageDataModel = YoutubePageDataModel(
    songs = songs?.map { it.mapToData() }.orEmpty(),
    playlists = playlists?.map { it.mapToData() }.orEmpty(),
    albums = albums?.map { it.mapToData() }.orEmpty(),
    artists = artists?.map { it.mapToData() }.orEmpty()
)

internal fun Item.SongItem.mapToData(): ItemData.SongItem = ItemData.SongItem(
    info = this.info.mapToData(),
    authors = this.authors?.map { it.mapToData() }.orEmpty(),
    album = this.album.mapToData(),
    durationText = this.durationText.orEmpty(),
    thumbnail = this.thumbnail.mapToData(),
)

internal fun Item.PlaylistItem.mapToData(): ItemData.PlaylistItem = ItemData.PlaylistItem(
    info = this.info.mapToData(),
    channel = this.channel.mapToData(),
    songCount = this.songCount ?: 0,
    thumbnail = this.thumbnail.mapToData()
)

internal fun Item.AlbumItem.mapToData(): ItemData.AlbumItem = ItemData.AlbumItem(
    info = this.info.mapToData(),
    authors = this.authors?.map { it.mapToData() }.orEmpty(),
    year = this.year.orEmpty(),
    thumbnail = this.thumbnail.mapToData()
)

internal fun Item.ArtistItem.mapToData(): ItemData.ArtistItem = ItemData.ArtistItem(
    info = this.info.mapToData(),
    subscribersCountText = this.subscribersCountText.orEmpty(),
    thumbnail = this.thumbnail.mapToData()
)

internal fun Thumbnail?.mapToData(): ThumbnailDataModel = ThumbnailDataModel(
    url = this?.url.orEmpty(),
)

internal fun Info<NavigationEndpoint.Endpoint.Watch>?.mapToData(): WatchDataModel = WatchDataModel(
    params = this?.endpoint?.params.orEmpty(),
    playlistId = this?.endpoint?.playlistId.orEmpty(),
    videoId = this?.endpoint?.videoId.orEmpty(),
    index = this?.endpoint?.index ?: 0,
    playlistSetVideoId = this?.endpoint?.playlistSetVideoId.orEmpty(),
    musicVideoType = this?.endpoint?.type.orEmpty(),
    name = this?.name.orEmpty()
)

internal fun Info<NavigationEndpoint.Endpoint.Browse>?.mapToData(): BrowseDataModel =
    BrowseDataModel(
        params = this?.endpoint?.params.orEmpty(),
        browseId = this?.endpoint?.params.orEmpty(),
        pageType = this?.endpoint?.type.orEmpty(),
        name = this?.name.orEmpty()
    )