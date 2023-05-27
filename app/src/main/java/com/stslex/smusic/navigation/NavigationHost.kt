package com.stslex.smusic.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.favourite.navigation.favouriteGraph
import com.stslex.feature.player.navigation.playerGraph
import com.stslex.feature.recommendation.navigation.recommendationGraph
import com.stslex.feature.search.navigation.searchGraph
import com.stslex.feature.settings.navigation.settingsGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavDestination.RECOMMENDATION.route
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        recommendationGraph(
            modifier = modifier,
            navigate = navController::navigate
        )
        settingsGraph(
            modifier = modifier,
            navigate = navController::navigate
        )
        favouriteGraph(
            modifier = modifier,
            navigate = navController::navigate
        )
        searchGraph(
            modifier = modifier,
            navigate = navController::navigate
        )
        playerGraph(
            modifier = modifier,
            navigate = navController::navigate
        )
    }
}

fun NavHostController.navigate(navigationScreen: NavigationScreen) {
    if (navigationScreen.destination == NavDestination.BACK) {
        popBackStack()
    } else {
        navigate(navigationScreen.destination.route)
    }
}
