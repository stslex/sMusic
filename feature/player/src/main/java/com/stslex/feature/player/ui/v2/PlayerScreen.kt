package com.stslex.feature.player.ui.v2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaItem
import androidx.palette.graphics.Palette
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.extensions.toDp
import com.stslex.core.ui.extensions.toPx
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.v1.components.PlayerControllerContainer
import com.stslex.feature.player.ui.v1.components.SongCover
import com.stslex.feature.player.ui.v1.components.SongInfoHeader
import com.stslex.feature.player.ui.v1.components.SongProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerScreen(
    currentMediaItem: () -> StateFlow<MediaItem?>,
    simpleMediaState: () -> StateFlow<SimpleMediaState>,
    allMediaItems: () -> StateFlow<List<MediaItem>>,
    onPlayerClick: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
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


    val mediaState by remember {
        simpleMediaState()
    }.collectAsState()

    val mediaItem by remember {
        currentMediaItem()
    }.collectAsState()

    val allItems by remember {
        allMediaItems()
    }.collectAsState()

    var mutedSwatch by remember {
        mutableStateOf<Palette.Swatch?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    val backgroundColor by animateColorAsState(
        targetValue = mutedSwatch?.rgb?.let(::Color) ?: MaterialTheme.colorScheme.background,
        label = "background color",
        animationSpec = tween(600)
    )

    val textTitleColor by animateColorAsState(
        targetValue = mutedSwatch?.titleTextColor?.let(::Color)
            ?: MaterialTheme.colorScheme.onSurface,
        label = "text title color",
        animationSpec = tween(600)
    )

    val textBodyColor by animateColorAsState(
        targetValue = mutedSwatch?.bodyTextColor?.let(::Color)
            ?: MaterialTheme.colorScheme.onSurfaceVariant,
        label = "text body color",
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                .background(backgroundColor)
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

                    val statusBarPadding = WindowInsets.statusBars
                        .asPaddingValues()
                        .let {
                            it.calculateTopPadding()
                        }


                    val paddingTop = remember(swipeableOffset, swipeProgress) {
                        (swipeableOffsetDp - screenHeight + statusBarPadding - 32.dp * swipeProgress)
                            .coerceAtLeast(0.dp)
                    }

                    SongCover(
                        modifier = Modifier
                            .padding(top = paddingTop)
                            .size(imageSize)
                            .align(Alignment.Start),
                        currentId = mediaItem?.mediaId.orEmpty(),
                        allMediaItems = allItems,
                        sendPlayerEvent = onPlayerClick,
                        onCurrentItem = { result ->
                            coroutineScope.launch(Dispatchers.Main) {
                                val palette = Palette.Builder(result.drawable.toBitmap()).generate()
                                mutedSwatch = palette.mutedSwatch
                            }
                        },
                        swipeProgress = swipeProgress
                    )

                    AnimatedVisibility(
                        visible = swipeableState.currentValue == SwipeState.EXPAND
                    ) {
                        LazyColumn {
                            item { Spacer(modifier = Modifier.padding(8.dp)) }
                            item {
                                SongInfoHeader(
                                    modifier = Modifier
                                        .padding(horizontal = 32.dp),
                                    song = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                                    artist = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                                    titleColor = textTitleColor,
                                    bodyColor = textBodyColor
                                )
                            }
                            item { Spacer(modifier = Modifier.padding(16.dp)) }
                            item {
                                PlayerControllerContainer(
                                    modifier = Modifier
                                        .padding(horizontal = 32.dp),
                                    sendPlayerEvent = onPlayerClick,
                                    playerPlayingState = mediaState.playerPlayingState
                                )
                            }
                            item { Spacer(modifier = Modifier.padding(8.dp)) }
                            item {
                                SongProgressBar(
                                    modifier = Modifier
                                        .padding(horizontal = 32.dp),
                                    mediaState = mediaState,
                                    updateProgress = { progress ->
                                        onPlayerClick(PlayerEvent.UpdateProgress(progress))
                                    }
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    visible = swipeableState.currentValue == SwipeState.SHRINK
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(
                                start = 16.dp
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                            style = MaterialTheme.typography.titleMedium,
                            color = textTitleColor,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = textBodyColor,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

enum class SwipeState {
    SHRINK,
    EXPAND
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
                currentMediaItem = { MutableStateFlow<MediaItem?>(null) },
                simpleMediaState = { MutableStateFlow(SimpleMediaState()) },
                allMediaItems = { MutableStateFlow(emptyList()) },
                onPlayerClick = {}
            )
        }
    }
}