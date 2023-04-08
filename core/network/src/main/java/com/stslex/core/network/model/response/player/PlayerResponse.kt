package com.stslex.core.network.model.response.player

import kotlinx.serialization.Serializable

@Serializable
internal data class PlayerResponse(
    val playabilityStatus: PlayabilityStatus?,
    val playerConfig: PlayerConfig?,
    val streamingData: StreamingData?,
    val videoDetails: VideoDetails?,
) {
    @Serializable
    internal data class PlayabilityStatus(
        val status: String?
    )

    @Serializable
    internal data class PlayerConfig(
        val audioConfig: AudioConfig?
    ) {
        @Serializable
        internal data class AudioConfig(
            private val loudnessDb: Double?
        ) {
            // For music clients only
            val normalizedLoudnessDb: Float?
                get() = loudnessDb?.plus(7)?.toFloat()
        }
    }

    @Serializable
    internal data class StreamingData(
        val adaptiveFormats: List<AdaptiveFormat>?
    ) {
        val highestQualityFormat: AdaptiveFormat?
            get() = adaptiveFormats?.findLast { it.itag == 251 || it.itag == 140 }

        @Serializable
        internal data class AdaptiveFormat(
            val itag: Int,
            val mimeType: String,
            val bitrate: Long?,
            val averageBitrate: Long?,
            val contentLength: Long?,
            val audioQuality: String?,
            val approxDurationMs: Long?,
            val lastModified: Long?,
            val loudnessDb: Double?,
            val audioSampleRate: Int?,
            val url: String?,
        )
    }

    @Serializable
    internal data class VideoDetails(
        val videoId: String?
    )
}