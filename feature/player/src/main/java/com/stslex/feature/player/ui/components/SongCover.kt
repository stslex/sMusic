package com.stslex.feature.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.ui.extensions.toDp
import kotlin.math.absoluteValue

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
            .takeIf { it > -1 }
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

    LaunchedEffect(key1 = currentId) {
        val page = allMediaItems
            .indexOfFirst {
                it.mediaId == currentId
            }
            .takeIf { it > -1 }
            ?: 0
        if (pagerState.currentPage == page) return@LaunchedEffect
        pagerState.animateScrollToPage(page)
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
            key = { page -> page }
        ) { page ->

            val context = LocalContext.current
            val uri = allMediaItems.getOrNull(page)?.mediaMetadata?.artworkUri

            AsyncImage(
                modifier = Modifier
                    .size(sizeDp)
                    .padding(32.dp)
                    .animatePager(pagerState, page),
                model = ImageRequest.Builder(context)
                    .data(uri)
                    .placeholderMemoryCacheKey(uri.toString())
                    .memoryCacheKey(uri.toString())
                    .diskCacheKey(uri.toString())
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                contentDescription = "song cover",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.animatePager(
    pagerState: PagerState,
    page: Int
): Modifier = graphicsLayer {
    val pageOffset = (
            (pagerState.currentPage - page) + pagerState
                .currentPageOffsetFraction
            )
        .absoluteValue

    val scale = lerp(
        0.75f,
        1f,
        1f - pageOffset.coerceIn(0f, 1f)
    )
    scaleX = scale
    scaleY = scale

    alpha = lerp(
        0.001f,
        1f,
        1f - pageOffset.coerceIn(0f, 1f)
    )
}