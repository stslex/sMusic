package com.stslex.feature.player.ui.v2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem

@Composable
fun PlayerShrinkContent(
    currentMediaItem: MediaItem?,
    colorCalculator: ColorCalculator,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .padding(
                start = 16.dp
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = currentMediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            color = colorCalculator.textTitleColor,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = currentMediaItem?.mediaMetadata?.title?.toString().orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = colorCalculator.textBodyColor,
            overflow = TextOverflow.Ellipsis
        )
    }
}
