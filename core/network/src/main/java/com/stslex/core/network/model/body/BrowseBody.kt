package com.stslex.core.network.model.body

import com.stslex.core.network.model.core.Context
import kotlinx.serialization.Serializable

@Serializable
data class BrowseBody(
    val context: Context = Context.DefaultWeb,
    val browseId: String,
    val params: String? = null
)