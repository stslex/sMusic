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
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun PlayerControllerContainer(
    onPlayerClick: (PlayerEvent) -> Unit,
    playerPlayingState: () -> Flow<PlayerPlayingState>,
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
            )
        }

        PlayerController(
            modifier = Modifier.weight(1f),
            onPlayerClick = onPlayerClick,
            playerPlayingState = playerPlayingState
        )

        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "like",
            )
        }

    }
}

@Composable
fun PlayerController(
    onPlayerClick: (PlayerEvent) -> Unit,
    playerPlayingState: () -> Flow<PlayerPlayingState>,
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
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            PlayerControllerPlay(
                onClick = remember {
                    { onPlayerClick(PlayerEvent.ResumePause) }
                },
                playerPlayingState = playerPlayingState
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
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun PlayerControllerPlay(
    onClick: () -> Unit,
    playerPlayingState: () -> Flow<PlayerPlayingState>,
    modifier: Modifier = Modifier
) {
    val playingState by remember {
        playerPlayingState()
    }.collectAsState(
        initial = PlayerPlayingState.PAUSE
    )

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
                onPlayerClick = {},
                playerPlayingState = { flowOf(PlayerPlayingState.PAUSE) })
        }
    }
}