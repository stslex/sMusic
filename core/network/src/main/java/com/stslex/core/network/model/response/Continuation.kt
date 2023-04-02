package com.stslex.core.network.model.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Continuation(
    @JsonNames("nextContinuationData", "nextRadioContinuationData")
    val nextContinuationData: Data?
) {
    @kotlinx.serialization.Serializable
    data class Data(
        val continuation: String?
    )
}