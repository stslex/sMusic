package com.stslex.core.network.model.response.browse

import kotlinx.serialization.Serializable

@Serializable
internal data class GridRenderer(
    val items: List<Item>?,
) {
    @Serializable
    internal data class Item(
        val musicTwoRowItemRenderer: MusicTwoRowItemRenderer?
    )
}