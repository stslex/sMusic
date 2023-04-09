package com.stslex.smusic.screen.player

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState

@Composable
fun PlayerPausePlayButton(
    modifier: Modifier = Modifier,
    playerPlayingState: PlayerPlayingState,
    onPlayerClick: (PlayerEvent) -> Unit
) {

    IconButton(
        modifier = modifier,
        onClick = {
            onPlayerClick(PlayerEvent.ResumePause)
        }
    ) {
        Icon(
            painter = painterResource(id = playerPlayingState.playingRes),
            contentDescription = null
        )
    }
}