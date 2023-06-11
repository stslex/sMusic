package com.stslex.feature.player.ui.v2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.feature.player.ui.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerInit(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = koinViewModel(),
) {
    PlayerScreen(
        currentMediaItem = viewModel::currentMediaItem,
        simpleMediaState = viewModel::simpleMediaState,
        allMediaItems = viewModel::allMediaItems,
        onPlayerClick = viewModel::onPlayerClick,
        modifier = modifier,
    )
}