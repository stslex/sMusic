package com.stslex.feature.player.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.feature.player.ui.base.ColorCalculator
import com.stslex.feature.player.ui.components.PlayerControllerContainer
import com.stslex.feature.player.ui.components.SongInfoHeader
import com.stslex.feature.player.ui.components.SongProgressBar

@Composable
fun PlayerExpandContent(
    currentMediaItem: MediaItem?,
    simpleMediaState: SimpleMediaState,
    colorCalculator: ColorCalculator,
    onPlayerClick: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item { Spacer(modifier = Modifier.padding(8.dp)) }
        item {
            SongInfoHeader(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                song = currentMediaItem?.mediaMetadata?.title?.toString()
                    .orEmpty(),
                artist = currentMediaItem?.mediaMetadata?.artist?.toString()
                    .orEmpty(),
                colorCalculator = colorCalculator
            )
        }
        item { Spacer(modifier = Modifier.padding(16.dp)) }
        item {
            PlayerControllerContainer(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                sendPlayerEvent = onPlayerClick,
                playerPlayingState = simpleMediaState.playerPlayingState,
                colorCalculator = colorCalculator
            )
        }
        item { Spacer(modifier = Modifier.padding(8.dp)) }
        item {
            SongProgressBar(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                mediaState = simpleMediaState,
                updateProgress = { progress ->
                    onPlayerClick(PlayerEvent.UpdateProgress(progress))
                },
                colorCalculator = colorCalculator
            )
        }
    }
}