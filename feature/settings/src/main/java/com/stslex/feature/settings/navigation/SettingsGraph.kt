package com.stslex.feature.settings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.settings.ui.SettingsScreen
import org.koin.androidx.compose.koinViewModel

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