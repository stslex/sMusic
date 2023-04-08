package com.stslex.core.network.model.response.browse

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Runs
import com.stslex.core.network.model.response.ThumbnailRenderer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
internal data class MusicResponsiveListItemRenderer(
    val fixedColumns: List<FlexColumn>?,
    val flexColumns: List<FlexColumn>,
    val thumbnail: ThumbnailRenderer?,
    val navigationEndpoint: NavigationEndpoint?,
) {
    @Serializable
    internal data class FlexColumn(
        @JsonNames("musicResponsiveListItemFixedColumnRenderer")
        val musicResponsiveListItemFlexColumnRenderer: MusicResponsiveListItemFlexColumnRenderer?
    ) {
        @Serializable
        internal data class MusicResponsiveListItemFlexColumnRenderer(
            val text: Runs?
        )
    }
}