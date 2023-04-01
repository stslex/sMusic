package com.stslex.feature.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {}
) {
    composable(route = NavDestination.HOME.route) {
        HomeScreen(
            modifier = modifier,
            navigate = navigate
        )
    }
}