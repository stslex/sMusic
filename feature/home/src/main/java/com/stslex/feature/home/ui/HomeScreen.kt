package com.stslex.feature.home.ui

import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import coil.compose.AsyncImage
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.network.model.Value
import com.stslex.core.ui.extensions.animatedOnBackground
import com.stslex.core.ui.extensions.toPx
import org.koin.core.component.getScopeName
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit,
    viewModel: HomeViewModel
) {
    val recommendations by remember(viewModel) {
        viewModel.recommendations
    }.collectAsState()

    val items = recommendations.songs.dropLast(1)

    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
            .build()
    }
    val mediaSession = remember {
        MediaSession
            .Builder(context, player)
            .build()
    }

    val playerInfoState by remember(viewModel) {
        viewModel.playerUrl
    }.collectAsState()

    val selectedItem = remember {
        mutableStateOf<ItemData.SongItem?>(null)
    }

    LaunchedEffect(key1 = selectedItem.value) {
        selectedItem.value?.let {
            viewModel.getPlayerData(id = it.key)
        }
    }

    when (val state = playerInfoState) {

        is Value.Loading -> Unit

        is Value.Content -> selectedItem.value?.let { item ->
            val url = state.data
                .streamingData
                .highestQualityFormat
                ?.url
                .orEmpty()
                .toUri()
            val mediaItem = item.asMediaItem(url)
            mediaSession.player.setMediaItem(mediaItem)
            mediaSession.player.prepare()
            mediaSession.player.play()
        }

        is Value.Error -> {
            mediaSession.player.stop()
            mediaSession.player.release()
            Log.d(currentRecomposeScope.getScopeName().value, state.error.message.orEmpty())
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = items,
            key = { it.key }
        ) { item ->
            Song(
                songItem = item,
                onClick = {
                    if (selectedItem.value == item) {
                        mediaSession.player.pause()
                        selectedItem.value = null
                    } else {
                        selectedItem.value = item
                    }
                },
                modifier = Modifier
                    .background(
                        if (selectedItem.value?.key == item.key) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
            )
        }
    }
}

val ItemData.SongItem.asMediaItem: (Uri) -> MediaItem
    @OptIn(UnstableApi::class)
    get() = { uri ->
        MediaItem.Builder()
            .setMediaId(key)
            .setUri(uri)
            .setCustomCacheKey(key)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(info.name)
                    .setArtist(authors.joinToString("") { it.name })
                    .setAlbumTitle(album.name)
                    .setArtworkUri(thumbnail.url.toUri())
                    .setExtras(
                        bundleOf(
                            "albumId" to album.browseId,
                            "durationText" to durationText,
                            "artistNames" to authors.map { it.name },
                            "artistIds" to authors.map { it.browseId },
                        )
                    )
                    .build()
            )
            .build()
    }

@Composable
fun Song(
    modifier: Modifier = Modifier,
    songItem: ItemData.SongItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable(onClick = onClick)
            .then(modifier),

        ) {
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = songItem.thumbnail.size(50.dp.toPx().roundToInt()),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier,
                    text = songItem.authors
                        ?.joinToString {
                            it.name.orEmpty().plus(" ")
                        }
                        .orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    color = animatedOnBackground().value,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    modifier = Modifier,
                    text = songItem.info?.name.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = animatedOnBackground().value,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(
            modifier = Modifier,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null
            )
        }

        IconButton(
            modifier = Modifier,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = null
            )
        }
    }
}
