package com.stslex.feature.favourite.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.favourite.ui.FavouriteScreen
import org.koin.androidx.compose.koinViewModel

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