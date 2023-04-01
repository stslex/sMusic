package com.stslex.smusic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stslex.core.navigation.NavDestination
import com.stslex.feature.home.navigation.homeGraph
import com.stslex.feature.settings.navigation.settingsGraph

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavDestination.HOME.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeGraph(modifier = modifier)
        settingsGraph(modifier = modifier)
    }
}

fun NavHostController.navToSettings() {
    navigate(NavDestination.SETTINGS.route)
}

