package com.stslex.core.network.model.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
internal data class ThumbnailRenderer(
    @JsonNames("croppedSquareThumbnailRenderer")
    val musicThumbnailRenderer: MusicThumbnailRenderer?
) {
    @Serializable
    internal data class MusicThumbnailRenderer(
        val thumbnail: Thumbnail?
    ) {
        @Serializable
        internal data class Thumbnail(
            val thumbnails: List<com.stslex.core.network.model.response.Thumbnail>?
        )
    }
}