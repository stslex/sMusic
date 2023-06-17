package com.stslex.smusic.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun PaddingValues.animatePaddingValues(): PaddingValues {
    val configuration = LocalConfiguration.current
    val layoutDirection = remember {
        if (configuration.layoutDirection == android.util.LayoutDirection.RTL) {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
    }
    val startPadding = animateDpAsState(
        targetValue = calculateStartPadding(layoutDirection),
        label = "startPadding",
        animationSpec = tween(300)
    )
    val endPadding = animateDpAsState(
        targetValue = calculateEndPadding(layoutDirection),
        label = "endPadding",
        animationSpec = tween(300)
    )

    val topPadding = animateDpAsState(
        targetValue = calculateTopPadding(),
        label = "topPadding",
        animationSpec = tween(300)
    )

    val bottomPadding = animateDpAsState(
        targetValue = calculateBottomPadding(),
        label = "bottomPadding",
        animationSpec = tween(300)
    )
    return remember {
        PaddingValues(
            start = startPadding.value,
            end = endPadding.value,
            top = topPadding.value,
            bottom = bottomPadding.value
        )
    }
}

private var lastScrollTime = 0L

enum class SwipeAction {
    LEFT, RIGHT
}

fun Modifier.swipeAction(
    action: (SwipeAction) -> Unit
): Modifier = pointerInput(Unit) {
    detectHorizontalDragGestures { _, dragAmount ->
        if (System.currentTimeMillis() - lastScrollTime <= 300) return@detectHorizontalDragGestures
        if (dragAmount > 0) {
            lastScrollTime = System.currentTimeMillis()
            action(SwipeAction.LEFT)
        } else if (dragAmount < 0) {
            lastScrollTime = System.currentTimeMillis()
            action(SwipeAction.RIGHT)
        }
    }
}