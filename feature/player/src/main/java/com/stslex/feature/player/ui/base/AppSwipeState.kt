package com.stslex.feature.player.ui.base

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.extensions.toPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Stable
data class AppSwipeState(
    val state: SwipeableState<SwipeState>,
    val screenHeight: Float,
    val coroutineScope: CoroutineScope
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

    fun expand() {
        animateTo(SwipeState.EXPAND)
    }

    fun collapse() {
        animateTo(SwipeState.SHRINK)
    }

    private fun animateTo(swipeState: SwipeState) {
        coroutineScope.launch {
            state.animateTo(
                targetValue = swipeState,
                anim = tween(
                    durationMillis = ANIMATION_DURATION,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 300
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberSwipeableState(): AppSwipeState {
    val swipeableState = androidx.compose.material.rememberSwipeableState(
        initialValue = SwipeState.SHRINK
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx
    val coroutineScope = rememberCoroutineScope()
    return remember {
        AppSwipeState(
            state = swipeableState,
            screenHeight = screenHeight,
            coroutineScope = coroutineScope
        )
    }
}