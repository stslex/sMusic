package com.stslex.feature.player.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import kotlinx.coroutines.flow.Flow

@Composable
fun PlayerControllers(
    onPlayerClick: (PlayerEvent) -> Unit,
    playerPlayingState: () -> Flow<PlayerPlayingState>,
    modifier: Modifier = Modifier
) {
    val playingState by remember {
        playerPlayingState()
    }.collectAsState(
        initial = PlayerPlayingState.PAUSE
    )

    Row(
        modifier = modifier
            .wrapContentWidth()
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

        IconButton(
            onClick = {
                onPlayerClick(PlayerEvent.ResumePause)
            }
        ) {
            Icon(
                painter = painterResource(id = playingState.playingRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

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