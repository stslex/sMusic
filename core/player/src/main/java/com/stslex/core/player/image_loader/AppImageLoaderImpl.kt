package com.stslex.core.player.image_loader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppImageLoaderImpl(
    private val context: Context
) : AppImageLoader {

    private var loadJob: Job = Job()

    @OptIn(DelicateCoroutinesApi::class)
    override fun invoke(
        uri: Uri?,
        onLoad: (Bitmap) -> Unit
    ) {
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(uri)
            .placeholderMemoryCacheKey(uri.toString())
            .memoryCacheKey(uri.toString())
            .diskCacheKey(uri.toString())
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()

        loadJob.cancel()
        loadJob = GlobalScope.launch {
            val bitmap = imageLoader
                .execute(imageRequest)
                .drawable
                ?.toBitmap()
                ?: return@launch
            onLoad(bitmap)
        }
    }
}