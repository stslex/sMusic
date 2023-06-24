package com.stslex.feature.player.ui.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest

class ColorCalculator(
    private val context: Context
) {
    private val imageLoader = ImageLoader(context)
    private var mutedSwatch by mutableStateOf<Palette.Swatch?>(null)
    var condition by mutableStateOf(false)

    val backgroundColor: Color
        @Composable
        get() {
            val color by animateColorAsState(
                targetValue = if (condition) {
                    mutedSwatch
                        ?.rgb
                        ?.let(::Color)
                        ?: MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.background
                },
                label = "background color",
                animationSpec = tween(600)
            )
            return color
        }

    val contentColor: Color
        @Composable
        get() = contentColorFor(
            backgroundColor = backgroundColor
        )

    val textBodyColor: Color
        @Composable
        get() {
            val color by animateColorAsState(
                targetValue = if (condition) {
                    mutedSwatch
                        ?.bodyTextColor
                        ?.let(::Color)
                        ?: MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                label = "background color",
                animationSpec = tween(600)
            )
            return color
        }

    val textTitleColor: Color
        @Composable
        get() {
            val color by animateColorAsState(
                targetValue = if (condition) {
                    mutedSwatch
                        ?.titleTextColor
                        ?.let(::Color)
                        ?: MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                label = "background color",
                animationSpec = tween(600)
            )
            return color
        }

    suspend fun calculate(uri: Uri?) {
        if (uri == null) return
        imageLoader.execute(imageRequest(uri))
    }

    private fun imageRequest(
        uri: Uri
    ): ImageRequest = ImageRequest.Builder(context)
        .data(uri)
        .bitmapConfig(Bitmap.Config.RGBA_F16)
        .placeholderMemoryCacheKey(uri.toString())
        .memoryCacheKey(uri.toString())
        .diskCacheKey(uri.toString())
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .allowHardware(false)
        .listener { _, result ->
            calculate(result.drawable)
        }
        .build()

    private fun calculate(drawable: Drawable) {
        val bitmap = drawable.toBitmap()
        val palette = Palette.Builder(bitmap).generate()
        mutedSwatch = palette.mutedSwatch
    }
}

@Composable
fun rememberColorCalculator(): ColorCalculator {
    val context = LocalContext.current
    return remember {
        ColorCalculator(
            context = context
        )
    }
}
