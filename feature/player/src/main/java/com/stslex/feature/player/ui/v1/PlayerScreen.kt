package com.stslex.feature.player.ui.v1

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.v1.components.PlayerControllerContainer
import com.stslex.feature.player.ui.v1.components.SongCover
import com.stslex.feature.player.ui.v1.components.SongInfoHeader
import com.stslex.feature.player.ui.v1.components.SongProgressBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlayerScreen(
    currentMediaItem: () -> StateFlow<MediaItem?>,
    simpleMediaState: () -> StateFlow<SimpleMediaState>,
    allMediaItems: () -> StateFlow<List<MediaItem>>,
    onPlayerClick: (PlayerEvent) -> Unit,
    navigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    val localConfiguration = LocalConfiguration.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val mediaItem by remember {
        currentMediaItem()
    }.collectAsState()

    val mediaState by remember {
        simpleMediaState()
    }.collectAsState()

    val mediaItems by remember {
        allMediaItems()
    }.collectAsState()

    var mutedSwatch by remember {
        mutableStateOf<Palette.Swatch?>(null)
    }

    val backgroundColor by animateColorAsState(
        targetValue = mutedSwatch
            ?.rgb
            ?.let(::Color)
            ?: MaterialTheme.colorScheme.background,
        label = "background color",
        animationSpec = tween(1000)
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

    LaunchedEffect(mediaItem?.mediaMetadata?.artworkUri) {
        val uri = mediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(uri)
            .placeholderMemoryCacheKey(uri.toString())
            .memoryCacheKey(uri.toString())
            .diskCacheKey(uri.toString())
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .allowHardware(false)
            .bitmapConfig(Bitmap.Config.RGBA_F16)
            .build()

        imageLoader
            .execute(imageRequest)
            .drawable
            ?.toBitmap()
            ?.let { bitmap ->
                mutedSwatch = Palette.Builder(bitmap).generate().mutedSwatch
            }
    }

    if (localConfiguration.orientation == ORIENTATION_LANDSCAPE) {
        PlayerScreenLandscape(
            mediaItem = mediaItem,
            mediaState = mediaState,
            allMediaItems = mediaItems,
            sendPlayerEvent = onPlayerClick,
            navigate = navigate,
            modifier = modifier
                .background(backgroundColor)
        )
    } else {
        PlayerScreenPortrait(
            mediaItem = mediaItem,
            mediaState = mediaState,
            allMediaItems = mediaItems,
            sendPlayerEvent = onPlayerClick,
            navigate = navigate,
            modifier = modifier
                .background(backgroundColor)
        )
    }
}

@Composable
fun PlayerScreenLandscape(
    mediaItem: MediaItem?,
    mediaState: SimpleMediaState,
    allMediaItems: List<MediaItem>,
    sendPlayerEvent: (PlayerEvent) -> Unit,
    navigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        SongCover(
            currentId = mediaItem?.mediaId.orEmpty(),
            allMediaItems = allMediaItems,
            sendPlayerEvent = sendPlayerEvent
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                SongInfoHeader(
                    song = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                    artist = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                )
                Spacer(modifier = Modifier.padding(16.dp))
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                PlayerControllerContainer(
                    sendPlayerEvent = sendPlayerEvent,
                    playerPlayingState = mediaState.playerPlayingState
                )
                Spacer(modifier = Modifier.padding(8.dp))
                SongProgressBar(
                    mediaState = mediaState,
                    updateProgress = { progress ->
                        sendPlayerEvent(PlayerEvent.UpdateProgress(progress))
                    }
                )
            }
        }
    }
}

@Composable
fun PlayerScreenPortrait(
    mediaItem: MediaItem?,
    mediaState: SimpleMediaState,
    allMediaItems: List<MediaItem>,
    sendPlayerEvent: (PlayerEvent) -> Unit,
    navigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SongCover(
                    currentId = mediaItem?.mediaId.orEmpty(),
                    allMediaItems = allMediaItems,
                    sendPlayerEvent = sendPlayerEvent
                )
            }
            item { Spacer(modifier = Modifier.padding(8.dp)) }
            item {
                SongInfoHeader(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    song = mediaItem?.mediaMetadata?.title?.toString().orEmpty(),
                    artist = mediaItem?.mediaMetadata?.artist?.toString().orEmpty(),
                )
            }
            item { Spacer(modifier = Modifier.padding(16.dp)) }
            item {
                PlayerControllerContainer(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    sendPlayerEvent = sendPlayerEvent,
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
                        sendPlayerEvent(PlayerEvent.UpdateProgress(progress))
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
            allMediaItems = { MutableStateFlow(emptyList()) },
            onPlayerClick = {},
            navigate = {}
        )
    }
}