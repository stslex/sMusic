package com.stslex.core.network.utils

import com.stslex.core.network.model.response.next.NextResponse
import io.ktor.client.plugins.compression.ContentEncoding

internal object NetworkExt {

    fun ContentEncoding.Config.brotli(quality: Float? = null) {
        customEncoder(BrotliEncoder, quality)
    }

    val NextResponse.browseId: String
        get() = this
            .contents
            ?.singleColumnMusicWatchNextResultsRenderer
            ?.tabbedRenderer
            ?.watchNextTabbedResultsRenderer
            ?.tabs
            ?.getOrNull(2)
            ?.tabRenderer
            ?.endpoint
            ?.browseEndpoint
            ?.browseId
            .orEmpty()
}