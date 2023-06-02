package com.stslex.feature.search.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core.navigation.NavDestination
import com.stslex.core.navigation.NavigationScreen
import com.stslex.feature.search.ui.SearchScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.searchGraph(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
) {
    composable(route = NavDestination.SEARCH.route) {
        SearchScreen(
            modifier = modifier,
            navigate = navigate,
            viewModel = koinViewModel()
        )
    }
}