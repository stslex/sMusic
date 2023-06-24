package com.stslex.feature.player.ui.v2

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.extensions.toPx
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Stable
data class AppSwipeState(
    val state: SwipeableState<SwipeState>,
    val screenHeight: Float,
) {

    val swipeableOffset by derivedStateOf {
        abs(state.offset.value)
    }

    val swipeProgress: Float
        get() = swipeableOffset / screenHeight

    val isShrink by derivedStateOf {
        state.currentValue == SwipeState.SHRINK
    }

    val isExpand by derivedStateOf {
        state.currentValue == SwipeState.EXPAND
    }

    val anchors: Map<Float, SwipeState> = mapOf(
        0f to SwipeState.SHRINK,
        -screenHeight to SwipeState.EXPAND
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberSwipeableState(): AppSwipeState {
    val swipeableState = androidx.compose.material.rememberSwipeableState(
        initialValue = SwipeState.SHRINK
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx
    return remember {
        AppSwipeState(
            state = swipeableState,
            screenHeight = screenHeight
        )
    }
}