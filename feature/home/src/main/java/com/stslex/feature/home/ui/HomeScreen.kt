package com.stslex.feature.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.stslex.core.navigation.NavigationScreen
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit,
    viewModel: HomeViewModel
) {
    val loadedPage by remember(viewModel) {
        viewModel.pageResult
    }.collectAsState()


    val lazyListState = rememberLazyListState()

    val items = loadedPage.songs?.dropLast(1) ?: emptyList()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState),
    ) {
        items(
            items = items,
            key = { item ->
                item.key
            }
        ) { song ->
            val currentIndex = items.indexOf(song)

            SongItem(
                song = song,
                lazyListState = lazyListState,
                index = currentIndex
            )
        }
    }
}

fun Modifier.setScrollingColumnAnimation(
    lazyListState: LazyListState,
    id: String?
): Modifier = graphicsLayer {
    val normalizedPosition = lazyListState.normalizedPosition(id).absoluteValue
    val value = 1 - (normalizedPosition * 0.05f)
    val alphaValue = 1 - (normalizedPosition * 0.15f)
    alpha = alphaValue
    scaleX = value
    scaleY = value
}

fun LazyListState.normalizedPosition(key: String?): Float = with(layoutInfo) {
    visibleItemsInfo.firstOrNull {
        it.key == key
    }?.let {
        val center = (viewportEndOffset + viewportStartOffset - it.size) / 2F
        (it.offset.toFloat() - center) / center
    } ?: 0F
}