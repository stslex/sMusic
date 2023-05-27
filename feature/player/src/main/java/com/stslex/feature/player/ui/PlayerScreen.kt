package com.stslex.feature.player.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.components.PlayerControllers
import com.stslex.feature.player.ui.components.SongCover
import com.stslex.feature.player.ui.components.SongInfoHeader
import com.stslex.feature.player.ui.components.SongProgressBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun PlayerScreen(
    currentMediaItem: () -> Flow<MediaItem?>,
    playerPlayingState: () -> Flow<PlayerPlayingState>,
    playerPlayingProgress: () -> Flow<SimpleMediaState.Progress>,
    onPlayerClick: (PlayerEvent) -> Unit,
    navigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    val mediaItem by remember {
        currentMediaItem()
    }.collectAsState(initial = null)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SongCover(
                    uri = mediaItem?.mediaMetadata?.artworkUri
                )
            }
            item { Spacer(modifier = Modifier.padding(8.dp)) }
            item {
                SongInfoHeader(
                    song = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                    artist = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                )
            }
            item { Spacer(modifier = Modifier.padding(8.dp)) }
            item {
                PlayerControllers(
                    onPlayerClick = onPlayerClick,
                    playerPlayingState = playerPlayingState
                )
            }
            item { Spacer(modifier = Modifier.padding(8.dp)) }
            item {
                SongProgressBar(
                    playerPlayingProgress = playerPlayingProgress
                )
            }
        }
    }
}

@Preview(
    device = "id:pixel_6", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PlayerScreenPreview() {
    AppTheme {

        PlayerScreen(
            currentMediaItem = { flowOf<MediaItem?>(null) },
            playerPlayingState = { flowOf(PlayerPlayingState.PLAY) },
            playerPlayingProgress = { flowOf(SimpleMediaState.Progress(30, 100)) },
            onPlayerClick = {},
            navigate = {}
        )
    }
}