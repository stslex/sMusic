package com.stslex.feature.player.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.player.ui.PlayerScreen
import com.stslex.feature.player.ui.PlayerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.playerGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
) {
    composable(
        route = NavDestination.PLAYER.route,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) },
    ) {
        val viewModel = koinViewModel<PlayerViewModel>()
        PlayerScreen(
            currentMediaItem = viewModel::currentMediaItem,
            simpleMediaState = viewModel::simpleMediaState,
            onPlayerClick = viewModel::onPlayerClick,
            navigate = navigate,
            modifier = modifier,
        )
    }
}