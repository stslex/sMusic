package com.stslex.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ButtonRenderer(
    val navigationEndpoint: NavigationEndpoint?
)