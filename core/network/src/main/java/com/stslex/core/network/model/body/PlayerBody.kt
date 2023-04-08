package com.stslex.core.network.model.body

import com.stslex.core.network.model.core.Context
import kotlinx.serialization.Serializable

@Serializable
data class PlayerBody(
    val context: Context = Context.DefaultAndroid,
    val videoId: String,
    val playlistId: String? = null
)

