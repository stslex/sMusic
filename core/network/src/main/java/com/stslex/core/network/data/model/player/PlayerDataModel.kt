package com.stslex.core.network.data.model.player

data class PlayerDataModel(
    val playabilityStatus: String,
    val loudnessDb: Float,
    val streamingData: PlayerStreamingDataModel,
    val videoId: String
)
