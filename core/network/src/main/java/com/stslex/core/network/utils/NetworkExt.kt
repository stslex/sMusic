package com.stslex.core.network.utils

import io.ktor.client.plugins.compression.ContentEncoding

object NetworkExt {

    fun ContentEncoding.Config.brotli(quality: Float? = null) {
        customEncoder(BrotliEncoder, quality)
    }
}