package com.stslex.feature.player.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.base.ColorCalculator
import com.stslex.feature.player.ui.base.rememberColorCalculator

@Composable
fun PlayerControllerContainer(
    sendPlayerEvent: (PlayerEvent) -> Unit,
    playerPlayingState: PlayerPlayingState,
    colorCalculator: ColorCalculator,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "like",
                tint = colorCalculator.contentColor
            )
        }

        PlayerController(
            modifier = Modifier.weight(1f),
            onPlayerClick = sendPlayerEvent,
            playerPlayingState = playerPlayingState,
            colorCalculator = colorCalculator
        )

        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "like",
                tint = colorCalculator.contentColor
            )
        }

    }
}

@Composable
fun PlayerController(
    onPlayerClick: (PlayerEvent) -> Unit,
    playerPlayingState: PlayerPlayingState,
    colorCalculator: ColorCalculator,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onPlayerClick(PlayerEvent.Previous)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = colorCalculator.contentColor
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            PlayerControllerPlay(
                onClick = remember {
                    { onPlayerClick(PlayerEvent.ResumePause) }
                },
                playingState = playerPlayingState,
                colorCalculator = colorCalculator
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            IconButton(
                onClick = {
                    onPlayerClick(PlayerEvent.Next)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colorCalculator.contentColor
                )
            }
        }
    }
}

@Composable
fun PlayerControllerPlay(
    onClick: () -> Unit,
    playingState: PlayerPlayingState,
    colorCalculator: ColorCalculator,
    modifier: Modifier = Modifier
) {

    val shapePercent by animateIntAsState(
        targetValue = when (playingState) {
            PlayerPlayingState.PAUSE -> 50
            PlayerPlayingState.PLAY -> 10
        },
        animationSpec = tween(600),
        label = "shapePercent"
    )

    val buttonColor by animateColorAsState(
        targetValue = when (playingState) {
            PlayerPlayingState.PAUSE -> MaterialTheme.colorScheme.primaryContainer
            PlayerPlayingState.PLAY -> MaterialTheme.colorScheme.tertiaryContainer
        },
        label = "buttonColor"
    )

    val iconButtonColor by animateColorAsState(
        targetValue = when (playingState) {
            PlayerPlayingState.PAUSE -> MaterialTheme.colorScheme.onPrimaryContainer
            PlayerPlayingState.PLAY -> MaterialTheme.colorScheme.onTertiaryContainer
        },
        label = "iconButtonColor"
    )

    Box(
        modifier = modifier
            .wrapContentSize()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(percent = shapePercent)
            )
            .clickable(onClick = onClick)
            .background(buttonColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(
                id = playingState.playingRes
            ),
            contentDescription = null,
            tint = iconButtonColor
        )
    }
}

@Preview
@Composable
fun PlayerControllersPreview() {
    AppTheme(isDarkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            PlayerControllerContainer(
                sendPlayerEvent = {},
                playerPlayingState = PlayerPlayingState.PAUSE,
                colorCalculator = rememberColorCalculator()
            )
        }
    }
}