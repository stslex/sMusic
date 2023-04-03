package com.stslex.core.network.model.response.browse

import com.stslex.core.network.model.response.ButtonRenderer
import com.stslex.core.network.model.response.Runs
import kotlinx.serialization.Serializable

@Serializable
internal data class MusicCarouselShelfRenderer(
    val header: Header?,
    val contents: List<Content>?,
) {
    @Serializable
    internal data class Content(
        val musicTwoRowItemRenderer: MusicTwoRowItemRenderer?,
        val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer?,
    )

    @Serializable
    internal data class Header(
        val musicTwoRowItemRenderer: MusicTwoRowItemRenderer?,
        val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer?,
        val musicCarouselShelfBasicHeaderRenderer: MusicCarouselShelfBasicHeaderRenderer?
    ) {
        @Serializable
        internal data class MusicCarouselShelfBasicHeaderRenderer(
            val moreContentButton: MoreContentButton?,
            val title: Runs?,
            val strapline: Runs?,
        ) {
            @Serializable
            internal data class MoreContentButton(
                val buttonRenderer: ButtonRenderer?
            )
        }
    }
}