package com.stslex.feature.settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.settings.ui.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {}
) {
    composable(route = NavDestination.SETTINGS.route) {
        SettingsScreen(
            modifier = modifier,
            navigate = navigate,
            viewModel = koinViewModel()
        )
    }
}