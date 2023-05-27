package com.stslex.feature.player.ui.components

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import coil.compose.AsyncImage
import com.stslex.core.ui.extensions.toDp

@Composable
fun SongCover(
    uri: Uri?,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current

    BoxWithConstraints(
        modifier = modifier.apply {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> fillMaxHeight()
                else -> fillMaxWidth()
            }
        },
        contentAlignment = Alignment.Center
    ) {
        val sizeDp = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> constraints.maxHeight
            else -> constraints.maxWidth
        }.toDp

        AsyncImage(
            modifier = Modifier
                .size(sizeDp)
                .background(Color.Yellow),
            model = uri,
            contentDescription = "song cover",
            contentScale = ContentScale.Crop,
        )
    }
}