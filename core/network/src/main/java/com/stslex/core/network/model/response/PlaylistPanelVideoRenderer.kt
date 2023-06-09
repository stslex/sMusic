package com.stslex.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class PlaylistPanelVideoRenderer(
    val title: Runs?,
    val longBylineText: Runs?,
    val shortBylineText: Runs?,
    val lengthText: Runs?,
    val navigationEndpoint: NavigationEndpoint?,
    val thumbnail: ThumbnailRenderer.MusicThumbnailRenderer.Thumbnail?,
)