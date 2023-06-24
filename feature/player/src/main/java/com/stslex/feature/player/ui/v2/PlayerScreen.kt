package com.stslex.feature.player.ui.v2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.extensions.toDp
import com.stslex.core.ui.extensions.toPx
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.v1.components.SongCover
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerScreen(
    simpleMediaState: SimpleMediaState,
    currentMediaItem: MediaItem?,
    allMediaItems: List<MediaItem>,
    onPlayerClick: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val initialHeight = 100.dp

    val swipeableState = rememberSwipeableState(
        initialValue = SwipeState.SHRINK
    )

    val swipeableOffset by remember {
        derivedStateOf {
            abs(swipeableState.offset.value)
        }
    }
    val swipeableOffsetDp = swipeableOffset.toDp

    val swipeProgress = remember(swipeableOffset) {
        swipeableOffsetDp / screenHeight
    }


    val colorCalculator = rememberColorCalculator()
        .apply {
            condition = swipeProgress > .2f
        }

    LaunchedEffect(currentMediaItem?.mediaMetadata?.artworkUri) {
        colorCalculator.calculate(
            uri = currentMediaItem?.mediaMetadata?.artworkUri
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .swipeable(
                    state = swipeableState,
                    anchors = mapOf(
                        0f to SwipeState.SHRINK,
                        -screenHeight.toPx to SwipeState.EXPAND
                    ),
                    orientation = Orientation.Vertical
                )
                .height(swipeableOffset.toDp + initialHeight)
                .clickable(
                    enabled = swipeableState.currentValue == SwipeState.SHRINK
                ) {
                    coroutineScope.launch {
                        swipeableState.animateTo(
                            targetValue = SwipeState.EXPAND,
                            anim = spring(0.9f)
                        )
                    }
                }
                .background(colorCalculator.backgroundColor)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .align(Alignment.Top)
                ) {

                    val screenWidth = configuration.screenWidthDp.dp
                    val swipeableOffsetHeight = swipeableOffsetDp + initialHeight
                    val imageSize = if (swipeableOffsetHeight < screenWidth) {
                        swipeableOffsetHeight
                    } else {
                        screenWidth
                    }

                    SongCover(
                        modifier = Modifier
                            .size(imageSize)
                            .align(Alignment.Start),
                        currentId = currentMediaItem?.mediaId.orEmpty(),
                        allMediaItems = allMediaItems,
                        sendPlayerEvent = onPlayerClick,
                        swipeProgress = swipeProgress
                    )

                    AnimatedVisibility(
                        visible = swipeableState.currentValue == SwipeState.EXPAND
                    ) {
                        PlayerExpandContent(
                            currentMediaItem = currentMediaItem,
                            simpleMediaState = simpleMediaState,
                            onPlayerClick = onPlayerClick,
                            colorCalculator = colorCalculator
                        )
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    visible = swipeableState.currentValue == SwipeState.SHRINK
                ) {
                    PlayerShrinkContent(
                        currentMediaItem = currentMediaItem,
                        colorCalculator = colorCalculator
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PlayerScreenPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            PlayerScreen(
                currentMediaItem = null,
                simpleMediaState = SimpleMediaState(),
                allMediaItems = emptyList(),
                onPlayerClick = {}
            )
        }
    }
}