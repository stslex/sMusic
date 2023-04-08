package com.stslex.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class Thumbnail(
    val url: String,
    val height: Int?,
    val width: Int?
) {
    val isResizable: Boolean
        get() = !url.startsWith("https://i.ytimg.com")

    fun size(size: Int): String {
        return when {
            url.startsWith("https://lh3.googleusercontent.com") -> "$url-w$size-h$size"
            url.startsWith("https://yt3.ggpht.com") -> "$url-s$size"
            else -> url
        }
    }
}