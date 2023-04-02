package com.stslex.feature.home.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stslex.core.network.data.model.Item
import com.stslex.core.ui.extensions.toPx
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.SongItem(
    modifier: Modifier = Modifier,
    song: Item.SongItem,
    lazyListState: LazyListState,
    index: Int
) {
    val density = LocalDensity.current
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val dpSize by remember {
        derivedStateOf {
            with(density) {
                DpSize(
                    width = size.width.toDp().plus(32.dp),
                    height = size.height.toDp().plus(32.dp)
                )
            }
        }
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            lazyListState.animateScrollToItem(index)
        }
    }

    val animatedBlur = animateDpAsState(
        targetValue = if (isClicked) 0.dp else 8.dp,
        animationSpec = tween(1000),
        label = "animatedBlur"
    )

    val imageSize = animateDpAsState(
        targetValue = if (isClicked) 300.dp else 200.dp,
        animationSpec = tween(1000),
        label = "imageSize"
    )

    Column(
        modifier = modifier
            .padding(8.dp)
            .animateItemPlacement(
                animationSpec = tween(1000)
            )
            .setScrollingColumnAnimation(lazyListState, song.key)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .size(dpSize)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        isClicked = isClicked.not()
                    }
                    .blur(animatedBlur.value),
                model = song.thumbnail?.size(200.dp.toPx().roundToInt()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.6f
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .onSizeChanged {
                        size = it
                    }
                    .background(Color.Transparent)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(imageSize.value)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    model = song.thumbnail?.size(200.dp.toPx().roundToInt()),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Column(
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        text = song.authors?.joinToString("") { it.name.orEmpty() }.orEmpty(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        text = song.info?.name.orEmpty(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
