package com.stslex.core.network.model.response.browse

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Runs
import com.stslex.core.network.model.response.ThumbnailRenderer
import kotlinx.serialization.Serializable

@Serializable
data class MusicTwoRowItemRenderer(
    val navigationEndpoint: NavigationEndpoint?,
    val thumbnailRenderer: ThumbnailRenderer?,
    val title: Runs?,
    val subtitle: Runs?,
)