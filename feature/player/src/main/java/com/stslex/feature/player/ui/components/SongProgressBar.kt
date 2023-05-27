package com.stslex.feature.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SongProgressBar(
    playerPlayingProgress: () -> Flow<SimpleMediaState.Progress>,
    modifier: Modifier = Modifier
) {
    val playingProgress by remember {
        playerPlayingProgress()
    }.collectAsState(
        initial = SimpleMediaState.Progress()
    )

    Column {
        LinearProgressIndicator(
            modifier = modifier.fillMaxWidth(),
            progress = playingProgress.progressPercentage
        )

        Spacer(modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = playingProgress.currentProgress,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = playingProgress.currentDuration,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun SongProgressBarPreview() {
    AppTheme {
        Box(
            Modifier
                .height(200.dp)
                .padding(
                    horizontal = 32.dp
                ),
            contentAlignment = Alignment.Center,
        ) {
            SongProgressBar(
                playerPlayingProgress = {
                    flowOf(
                        SimpleMediaState.Progress(
                            progress = 30L,
                            duration = 100L
                        )
                    )
                }
            )
        }
    }
}