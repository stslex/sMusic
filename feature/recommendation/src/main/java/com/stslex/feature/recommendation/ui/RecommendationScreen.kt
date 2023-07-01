package com.stslex.feature.recommendation.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.ui.components.setDynamicPlaceHolder
import com.stslex.core.ui.extensions.toDp
import com.stslex.core.ui.extensions.toPx

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit,
    viewModel: RecommendationViewModel
) {
    val configuration = LocalConfiguration.current
    val size = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> configuration.screenHeightDp.dp
        else -> configuration.screenWidthDp.dp
    }.toPx

    LaunchedEffect(key1 = Unit) {
        viewModel.init(size.toInt())
    }

    val items by remember {
        viewModel.recommendations
    }.collectAsState(emptyList())

    val currentPlayingMedia by remember {
        viewModel.currentPlayingMedia
    }.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        if (items.isEmpty()) {
            items(count = 10) {
                Song(
                    songItem = MediaItem.EMPTY,
                    onClick = {},
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface),
                    isPlaceHolder = true,
                    isSelected = false
                )
            }
        } else {
            items(
                items = items,
                key = { item -> item.mediaId }
            ) { item ->
                Song(
                    songItem = item,
                    onClick = {
                        viewModel.play(item.mediaId)
                    },
                    isSelected = currentPlayingMedia?.mediaId == item.mediaId,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Song(
    modifier: Modifier = Modifier,
    songItem: MediaItem,
    onClick: () -> Unit,
    isSelected: Boolean,
    isPlaceHolder: Boolean = false
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var imageWidth by remember {
                mutableStateOf(0)
            }
            AsyncImage(
                modifier = Modifier
                    .setDynamicPlaceHolder(isVisible = isPlaceHolder)
                    .fillMaxHeight()
                    .width(imageWidth.toDp)
                    .onGloballyPositioned {
                        imageWidth = it.size.height
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(songItem.mediaMetadata.artworkUri)
                    .placeholderMemoryCacheKey(songItem.mediaMetadata.artworkUri.toString())
                    .memoryCacheKey(songItem.mediaMetadata.artworkUri.toString())
                    .diskCacheKey(songItem.mediaMetadata.artworkUri.toString())
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(start = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.CenterStart)
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .setDynamicPlaceHolder(
                                isVisible = isPlaceHolder
                            ),
                        text = songItem.mediaMetadata.artist?.toString().orEmpty(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .setDynamicPlaceHolder(
                                isVisible = isPlaceHolder
                            ),
                        text = songItem.mediaMetadata.title?.toString().orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
        }
    }
}
