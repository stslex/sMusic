package com.stslex.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun AnimateProgressbar(
    progress: Float,
    modifier: Modifier = Modifier,
    colorLoader: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    colorProgress: Color = MaterialTheme.colorScheme.onSurface,
    loaderSize: Dp = 2.dp,
    progressSize: Dp = 4.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDraw = {
            val y = size.height * 0.5f
            val xProgress = size.width * progress
            val progressHeight = progressSize.toPx()

            drawLine(
                color = colorLoader,
                start = Offset(x = 0f, y = y),
                end = Offset(x = size.width, y = y),
                strokeWidth = loaderSize.toPx(),
                cap = StrokeCap.Round
            )

            drawLine(
                color = colorProgress,
                start = Offset(x = 0f, y = y),
                end = Offset(x = xProgress, y = y),
                strokeWidth = progressHeight,
                cap = StrokeCap.Round
            )

            drawCircle(
                color = colorProgress,
                center = Offset(
                    x = xProgress,
                    y = y
                ),
                radius = progressHeight * 1.5f,
            )
        }
    )
}

@Preview
@Composable
fun AnimateProgressbarPreview() {
    AppTheme {
        val progress = remember {
            mutableStateOf(0f)
        }
        LaunchedEffect(Unit) {
            suspend fun addProgress() {
                progress.value = (progress.value + 0.01f) % 1
                delay(10)
                addProgress()
            }
            addProgress()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimateProgressbar(
                progress = progress.value
            )
        }
    }
}