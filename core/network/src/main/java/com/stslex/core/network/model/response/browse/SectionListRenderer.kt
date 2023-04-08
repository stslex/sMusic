package com.stslex.core.network.model.response.browse

import com.stslex.core.network.model.response.Continuation
import com.stslex.core.network.model.response.Runs
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
internal data class SectionListRenderer(
    val contents: List<Content>?,
    val continuations: List<Continuation>?
) {
    @Serializable
    internal data class Content(
        @JsonNames("musicImmersiveCarouselShelfRenderer")
        val musicCarouselShelfRenderer: MusicCarouselShelfRenderer?,
        @JsonNames("musicPlaylistShelfRenderer")
        val musicShelfRenderer: MusicShelfRenderer?,
        val gridRenderer: GridRenderer?,
        val musicDescriptionShelfRenderer: MusicDescriptionShelfRenderer?,
    ) {

        @Serializable
        internal data class MusicDescriptionShelfRenderer(
            val description: Runs?,
        )
    }
}