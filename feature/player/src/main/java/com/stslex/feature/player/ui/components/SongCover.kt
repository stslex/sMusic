package com.stslex.feature.player.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.stslex.core.ui.extensions.toDp

@Composable
fun SongCover(
    uri: Uri?,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .size(constraints.maxWidth.toDp)
                .background(Color.Cyan),
            model = uri,
            contentDescription = "song cover",
            contentScale = ContentScale.Crop,
        )
    }
}