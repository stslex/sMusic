package com.stslex.feature.player.ui.v1.components

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.components.shader.test.ShaderText

@Composable
fun SongInfoHeader(
    song: String,
    artist: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    bodyColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ShaderText(
                text = song,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = titleColor,
                textAlign = TextAlign.Center,
            )
            ShaderText(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = artist,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = bodyColor,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = song,
                style = MaterialTheme.typography.titleLarge,
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = artist,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = bodyColor,
                textAlign = TextAlign.Center
            )
        }
    }
}