package com.stslex.smusic.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.components.setStaticPlaceHolder
import com.stslex.smusic.screen.SwipeAction
import com.stslex.smusic.screen.swipeAction
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty0

@Composable
fun PlayerContainer(
    modifier: Modifier = Modifier,
    currentMediaItem: KProperty0<Flow<MediaItem?>>,
    playerPlayingState: KProperty0<Flow<PlayerPlayingState>>,
    playerPlayingProgress: KProperty0<Flow<SimpleMediaState.Progress>>,
    onPlayerClick: (PlayerEvent) -> Unit
) {
    val mediaItem by remember {
        currentMediaItem.get()
    }.collectAsState(initial = null)

    val playingState by remember {
        playerPlayingState.get()
    }.collectAsState(initial = PlayerPlayingState.PAUSE)

    val playingProgress by remember {
        playerPlayingProgress.get()
    }.collectAsState(initial = SimpleMediaState.Progress(0L))

    val isShimmerVisible by remember {
        derivedStateOf { mediaItem == null }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable(
                onClick = {
                    // TODO Open Player View FullScreen
                }
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .swipeAction { action ->
                when (action) {
                    SwipeAction.LEFT -> onPlayerClick(PlayerEvent.Previous)
                    SwipeAction.RIGHT -> onPlayerClick(PlayerEvent.Next)
                }
            }
            .then(modifier),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .setStaticPlaceHolder(isVisible = isShimmerVisible),
                model = mediaItem?.mediaMetadata?.artworkUri,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            PlayerTextContainer(
                modifier = Modifier.weight(1f),
                artist = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                title = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                isShimmerVisible = isShimmerVisible
            )

            PlayerPausePlayButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                playerPlayingState = playingState,
                onPlayerClick = onPlayerClick
            )
        }

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            progress = playingProgress.progressPercentage,
            strokeCap = StrokeCap.Round
        )
    }
}
