package com.stslex.feature.player.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.stslex.feature.player.ui.PlayerScreen
import com.stslex.feature.player.ui.PlayerViewModel
import com.stslex.feature.player.ui.base.AppSwipeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerInit(
    swipeableState: AppSwipeState,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = koinViewModel(),
) {
    val simpleMediaState by remember {
        viewModel.simpleMediaState
    }.collectAsState()

    val currentMediaItem by remember {
        viewModel.currentMediaItem
    }.collectAsState()

    val allMediaItems by remember {
        viewModel.allMediaItems
    }.collectAsState()

    PlayerScreen(
        currentMediaItem = currentMediaItem,
        simpleMediaState = simpleMediaState,
        allMediaItems = allMediaItems,
        onPlayerClick = viewModel::onPlayerClick,
        modifier = modifier,
        swipeableState = swipeableState
    )
}