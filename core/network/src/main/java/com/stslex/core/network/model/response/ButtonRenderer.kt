package com.stslex.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class ButtonRenderer(
    val navigationEndpoint: NavigationEndpoint?
)