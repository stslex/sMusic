package com.stslex.core.network.data.model.player

data class PlayerAdaptiveFormat(
    val itag: Int,
    val mimeType: String,
    val bitrate: Long,
    val averageBitrate: Long,
    val contentLength: Long,
    val audioQuality: String,
    val approxDurationMs: Long,
    val lastModified: Long,
    val loudnessDb: Double,
    val audioSampleRate: Int,
    val url: String,
)