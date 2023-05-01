package com.stslex.feature.recommendation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.recommendation.ui.HomeScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.recommendationGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {}
) {
    composable(route = NavDestination.RECOMMENDATION.route) {
        HomeScreen(
            modifier = modifier,
            navigate = navigate,
            viewModel = koinViewModel()
        )
    }
}