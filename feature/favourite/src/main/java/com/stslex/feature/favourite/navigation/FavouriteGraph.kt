package com.stslex.feature.favourite.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.favourite.ui.FavouriteScreen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
) {
    composable(route = NavDestination.FAVOURITE.route) {
        FavouriteScreen(
            modifier = modifier,
            navigate = navigate,
            viewModel = koinViewModel()
        )
    }
}