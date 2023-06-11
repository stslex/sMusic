package com.stslex.feature.player.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.player.ui.PlayerScreen
import com.stslex.feature.player.ui.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.playerGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
) {
    composable(
        route = NavDestination.PLAYER.route,
    ) {
        val viewModel = koinViewModel<PlayerViewModel>()
        PlayerScreen(
            currentMediaItem = viewModel::currentMediaItem,
            simpleMediaState = viewModel::simpleMediaState,
            allMediaItems = viewModel::allMediaItems,
            onPlayerClick = viewModel::onPlayerClick,
            navigate = navigate,
            modifier = modifier,
        )
    }
}