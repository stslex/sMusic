package com.stslex.smusic.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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