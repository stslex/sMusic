package com.stslex.core.network.data.model.page

data class ThumbnailDataModel(
    val url: String = "",
) {

    companion object {
        private const val URL_YOUTUBE = "https://i.ytimg.com"
        private const val URL_GOOGLE_USER = "https://lh3.googleusercontent.com"
        private const val URL_GOOGLE_PICTURE = "https://yt3.ggpht.com"
        private const val PARAMETER_SIZE = "s"
        private const val PARAMETER_WIDTH = "w"
        private const val PARAMETER_HEIGHT = "h"
        private const val PARAMETER_SEPARATOR = "-"
    }

    val isResizable: Boolean
        get() = url.startsWith(URL_YOUTUBE).not()

    fun size(size: Int): String = size(
        width = size,
        height = size
    )

    fun size(width: Int, height: Int): String {
        val stringBuilder = StringBuilder(url)
        when {
            url.startsWith(URL_GOOGLE_USER) -> stringBuilder
                .append(PARAMETER_SEPARATOR)
                .append(PARAMETER_WIDTH)
                .append(width)
                .append(PARAMETER_SEPARATOR)
                .append(PARAMETER_HEIGHT)
                .append(height)

            url.startsWith(URL_GOOGLE_PICTURE) -> stringBuilder
                .append(PARAMETER_SEPARATOR)
                .append(PARAMETER_SIZE)
                .append(maxOf(width, height))
        }
        return stringBuilder.toString()
    }
}
