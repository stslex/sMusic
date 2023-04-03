package com.stslex.core.network.data.mappers

import com.stslex.core.network.data.model.player.PlayerAdaptiveFormat
import com.stslex.core.network.data.model.player.PlayerDataModel
import com.stslex.core.network.data.model.player.PlayerStreamingDataModel
import com.stslex.core.network.model.response.player.PlayerResponse

internal fun PlayerResponse.mapToData(): PlayerDataModel = PlayerDataModel(
    playabilityStatus = this.playabilityStatus?.status.orEmpty(),
    loudnessDb = this.playerConfig?.audioConfig?.normalizedLoudnessDb ?: 0f,
    streamingData = this.streamingData.mapToData(),
    videoId = this.videoDetails?.videoId.orEmpty()
)

internal fun PlayerResponse.StreamingData?.mapToData(): PlayerStreamingDataModel =
    PlayerStreamingDataModel(
        adaptiveFormats = this?.adaptiveFormats
            ?.map { adaptiveFormat ->
                adaptiveFormat.mapToData()
            }
            .orEmpty()
    )

internal fun PlayerResponse.StreamingData.AdaptiveFormat.mapToData(): PlayerAdaptiveFormat =
    PlayerAdaptiveFormat(
        itag = this.itag,
        mimeType = this.mimeType,
        bitrate = this.bitrate ?: 0L,
        averageBitrate = this.averageBitrate ?: 0L,
        contentLength = this.contentLength ?: 0L,
        audioQuality = this.audioQuality.orEmpty(),
        approxDurationMs = this.approxDurationMs ?: 0L,
        lastModified = this.lastModified ?: 0L,
        loudnessDb = this.loudnessDb ?: 0.0,
        audioSampleRate = this.audioSampleRate ?: 0,
        url = this.url.orEmpty(),
    )

