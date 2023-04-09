package com.stslex.feature.recommendation.ui

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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.stslex.core.ui.components.setDynamicPlaceHolder
import com.stslex.core.ui.extensions.animatedOnBackground
import com.stslex.core.ui.extensions.toPx
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit,
    viewModel: RecommendationViewModel
) {
    val recommendations by remember(viewModel) {
        viewModel.recommendations
    }.collectAsState(null)

    val items = recommendations?.songs?.dropLast(1).orEmpty()

    val currentPlayingMedia by remember(viewModel) {
        viewModel.currentPlayingMedia
    }.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        if (recommendations == null) {
            items(count = 10) {
                Song(
                    songItem = ItemData.SongItem(),
                    onClick = {},
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface),
                    isPlaceHolder = true
                )
            }
        } else {
            itemsIndexed(
                items = items,
                key = { index, item -> item.key }
            ) { index, item ->
                Song(
                    songItem = item,
                    onClick = {
                        viewModel.play(
                            songItem = item,
                            index = index
                        )
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
}

@Composable
fun Song(
    modifier: Modifier = Modifier,
    songItem: ItemData.SongItem,
    onClick: () -> Unit,
    isPlaceHolder: Boolean = false
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
            modifier = Modifier
                .setDynamicPlaceHolder(isVisible = isPlaceHolder)
                .size(50.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .setDynamicPlaceHolder(isVisible = isPlaceHolder),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .setDynamicPlaceHolder(isVisible = isPlaceHolder),
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
