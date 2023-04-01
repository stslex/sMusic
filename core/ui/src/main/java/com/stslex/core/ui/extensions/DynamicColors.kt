package com.stslex.core.ui.extensions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

@Composable
fun Color.animatedColor(
    label: String = "ColorAnimation",
    animationSpec: AnimationSpec<Color> = tween(1000),
    finishedListener: ((Color) -> Unit)? = null
): State<Color> {
    return animateColorAsState(
        targetValue = this,
        label = label,
        animationSpec = animationSpec,
        finishedListener = finishedListener
    )
}

val animatedBackground: @Composable () -> State<Color>
    get() = {
        MaterialTheme.colorScheme.background.animatedColor()
    }

val animatedOnBackground: @Composable () -> State<Color>
    get() = {
        MaterialTheme.colorScheme.onBackground.animatedColor()
    }

val animatedSurface: @Composable () -> State<Color>
    get() = {
        MaterialTheme.colorScheme.surface.animatedColor()
    }

val animatedOnSurface: @Composable () -> State<Color>
    get() = {
        MaterialTheme.colorScheme.onSurface.animatedColor()
    }