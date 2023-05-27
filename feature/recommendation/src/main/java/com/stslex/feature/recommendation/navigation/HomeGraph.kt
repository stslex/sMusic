package com.stslex.feature.recommendation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.recommendation.ui.HomeScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
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