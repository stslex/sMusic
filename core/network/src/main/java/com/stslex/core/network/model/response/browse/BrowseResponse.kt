package com.stslex.core.network.model.response.browse

import com.stslex.core.network.model.response.ButtonRenderer
import com.stslex.core.network.model.response.Runs
import com.stslex.core.network.model.response.ThumbnailRenderer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class BrowseResponse(
    val contents: Contents?,
    val header: Header?,
    val microformat: Microformat?
) {
    @Serializable
    internal data class Contents(
        val singleColumnBrowseResultsRenderer: Tabs?,
        val sectionListRenderer: SectionListRenderer?,
    )

    @Serializable
    internal data class Header @OptIn(ExperimentalSerializationApi::class) constructor(
        @JsonNames("musicVisualHeaderRenderer")
        val musicImmersiveHeaderRenderer: MusicImmersiveHeaderRenderer?,
        val musicDetailHeaderRenderer: MusicDetailHeaderRenderer?,
    ) {
        @Serializable
        internal data class MusicDetailHeaderRenderer(
            val title: Runs?,
            val subtitle: Runs?,
            val secondSubtitle: Runs?,
            val thumbnail: ThumbnailRenderer?,
        )

        @Serializable
        internal data class MusicImmersiveHeaderRenderer(
            val description: Runs?,
            val playButton: PlayButton?,
            val startRadioButton: StartRadioButton?,
            val thumbnail: ThumbnailRenderer?,
            val foregroundThumbnail: ThumbnailRenderer?,
            val title: Runs?
        ) {
            @Serializable
            internal data class PlayButton(
                val buttonRenderer: ButtonRenderer?
            )

            @Serializable
            internal data class StartRadioButton(
                val buttonRenderer: ButtonRenderer?
            )
        }
    }

    @Serializable
    internal data class Microformat(
        val microformatDataRenderer: MicroformatDataRenderer?
    ) {
        @Serializable
        internal data class MicroformatDataRenderer(
            val urlCanonical: String?
        )
    }
}