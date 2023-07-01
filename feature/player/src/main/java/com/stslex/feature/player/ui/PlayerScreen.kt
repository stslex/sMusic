package com.stslex.feature.player.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.extensions.toDp
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.base.AppSwipeState
import com.stslex.feature.player.ui.base.rememberColorCalculator
import com.stslex.feature.player.ui.base.rememberSwipeableState
import com.stslex.feature.player.ui.components.SongCover

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerScreen(
    simpleMediaState: SimpleMediaState,
    currentMediaItem: MediaItem?,
    allMediaItems: List<MediaItem>,
    swipeableState: AppSwipeState,
    onPlayerClick: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current

    val initialHeight = 100.dp

    val swipeableOffsetDp = swipeableState.swipeableOffset.toDp
    val colorCalculator = rememberColorCalculator()
        .apply {
            condition = swipeableState.swipeProgress > .2f
        }

    LaunchedEffect(currentMediaItem?.mediaMetadata?.artworkUri) {
        colorCalculator.calculate(
            uri = currentMediaItem?.mediaMetadata?.artworkUri
        )
    }

    Box(
        modifier = modifier
            .swipeable(
                state = swipeableState.state,
                anchors = swipeableState.anchors,
                orientation = Orientation.Vertical
            )
            .height(swipeableOffsetDp + initialHeight)
            .clickable(
                enabled = swipeableState.isShrink
            ) {
                swipeableState.expand()
            }
            .background(
                colorCalculator.backgroundColor.copy(
                    alpha = swipeableState.swipeProgress * 0.5f
                )
            )
            .fillMaxWidth()
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
                    swipeProgress = swipeableState.swipeProgress
                )

                AnimatedVisibility(
                    visible = swipeableState.isExpand
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
                visible = swipeableState.isShrink
            ) {
                PlayerShrinkContent(
                    currentMediaItem = currentMediaItem,
                    colorCalculator = colorCalculator
                )
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
                onPlayerClick = {},
                swipeableState = rememberSwipeableState()
            )
        }
    }
}