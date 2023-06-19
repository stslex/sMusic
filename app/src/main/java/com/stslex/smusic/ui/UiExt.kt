package com.stslex.smusic.ui

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

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