package com.stslex.feature.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.ui.extensions.toDp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongCover(
    currentId: String,
    allMediaItems: List<MediaItem>,
    sendPlayerEvent: (PlayerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val initialPage = remember {
        allMediaItems
            .indexOfFirst {
                it.mediaId == currentId
            }
            .takeIf { it >= -1 }
            ?: 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage
    )

    val selectedId by remember {
        derivedStateOf {
            allMediaItems.getOrNull(pagerState.currentPage)?.mediaId.orEmpty()
        }
    }

    LaunchedEffect(
        key1 = selectedId
    ) {
        sendPlayerEvent(
            PlayerEvent.SeekTo(selectedId, 0L)
        )
    }

    BoxWithConstraints(
        modifier = modifier.apply {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> fillMaxHeight()
                else -> fillMaxWidth()
            }
        },
        contentAlignment = Alignment.Center,
    ) {

        val sizeDp = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> constraints.maxHeight
            else -> constraints.maxWidth
        }.toDp

        HorizontalPager(
            modifier = Modifier
                .size(sizeDp),
            pageCount = allMediaItems.size,
            state = pagerState,
        ) { page ->

            AsyncImage(
                modifier = Modifier
                    .size(sizeDp)
                    .padding(32.dp)
                    .background(Color.Yellow),
                model = allMediaItems.getOrNull(page)?.mediaMetadata?.artworkUri,
                contentDescription = "song cover",
                contentScale = ContentScale.Crop,
            )
        }
    }
}