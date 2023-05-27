package com.stslex.feature.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.components.AnimateProgressbar
import com.stslex.core.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SongProgressBar(
    playerPlayingProgress: () -> Flow<SimpleMediaState.Progress>,
    updateProgress: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val playingProgress by remember {
        playerPlayingProgress()
    }.collectAsState(
        initial = SimpleMediaState.Progress()
    )

    BoxWithConstraints(
        modifier = modifier
    ) {
        val maxWidth = constraints.maxWidth
        Column {
            AnimateProgressbar(
                modifier = Modifier.pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val position = change.position.x
                        if (position == 0f) return@detectHorizontalDragGestures
                        val singleSize = position / maxWidth
                        updateProgress(singleSize)
                    }
                },
                progress = playingProgress.progressPercentage,
            )

            Spacer(modifier = Modifier.padding(4.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = playingProgress.currentProgress,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = playingProgress.currentDuration,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
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
                },
                updateProgress = {}
            )
        }
    }
}