package com.stslex.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

val Dp.toPx: Float
    @Composable
    get() = with(LocalDensity.current) {
        toPx()
    }