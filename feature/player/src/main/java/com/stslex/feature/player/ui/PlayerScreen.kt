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
import androidx.media3.common.MediaMetadata
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.components.PlayerControllerContainer
import com.stslex.feature.player.ui.components.SongCover
import com.stslex.feature.player.ui.components.SongInfoHeader
import com.stslex.feature.player.ui.components.SongProgressBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlayerScreen(
    currentMediaItem: () -> StateFlow<MediaItem?>,
    simpleMediaState: () -> StateFlow<SimpleMediaState>,
    onPlayerClick: (PlayerEvent) -> Unit,
    navigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    val mediaItem by remember {
        currentMediaItem()
    }.collectAsState()

    val mediaState by remember {
        simpleMediaState()
    }.collectAsState()

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
            item { Spacer(modifier = Modifier.padding(16.dp)) }
            item {
                PlayerControllerContainer(
                    onPlayerClick = onPlayerClick,
                    playerPlayingState = mediaState.playerPlayingState
                )
            }
            item { Spacer(modifier = Modifier.padding(8.dp)) }
            item {
                SongProgressBar(
                    mediaState = mediaState,
                    updateProgress = { progress ->
                        onPlayerClick(PlayerEvent.UpdateProgress(progress))
                    }
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
        val metaData = MediaMetadata.Builder()
            .setArtist("ArtistName")
            .setTitle("SongTitile")
            .build()
        val mediaData = MediaItem.Builder()
            .setMediaMetadata(metaData)
            .build()
        PlayerScreen(
            currentMediaItem = { MutableStateFlow(mediaData) },
            simpleMediaState = { MutableStateFlow(SimpleMediaState()) },
            onPlayerClick = {},
            navigate = {}
        )
    }
}