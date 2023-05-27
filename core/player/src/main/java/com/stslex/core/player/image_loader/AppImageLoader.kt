package com.stslex.core.player.image_loader

import android.graphics.Bitmap
import android.net.Uri

fun interface AppImageLoader {

    operator fun invoke(
        uri: Uri?,
        onLoad: (Bitmap) -> Unit
    )
}

