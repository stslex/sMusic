package com.stslex.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.network.data.model.page.ItemData
import com.stslex.core.ui.extensions.animatedOnBackground
import com.stslex.core.ui.extensions.toPx
import com.stslex.feature.home.utils.asMediaItem
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

    val currentPlayingMedia by remember(viewModel) {
        viewModel.currentPlayingMedia
    }.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(
            items = items,
            key = { it.key }
        ) { item ->
            Song(
                songItem = item,
                onClick = {
                    viewModel.play(item.asMediaItem)
                },
                modifier = Modifier
                    .background(
                        if (currentPlayingMedia?.mediaId == item.key) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
            )
        }
    }
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
            model = songItem.thumbnail.size(50.dp.toPx.roundToInt()),
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
                        .joinToString {
                            it.name.plus(" ")
                        },
                    style = MaterialTheme.typography.titleMedium,
                    color = animatedOnBackground().value,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    modifier = Modifier,
                    text = songItem.info.name,
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
