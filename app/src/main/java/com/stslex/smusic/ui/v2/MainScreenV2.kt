package com.stslex.smusic.ui.v2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.ui.v2.PlayerInit
import com.stslex.feature.player.ui.v2.rememberSwipeableState
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navigate
import com.stslex.smusic.ui.v1.appbar.AppTopAppBar

@Composable
fun MainScreenV2(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { currentDestination.value?.destination?.route.orEmpty() }
    }
    val swipeState = rememberSwipeableState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            topBar = {
                AppTopAppBar(
                    currentRoute = currentRoute,
                    navToSettings = {
                        navController.navigate(NavigationScreen.Settings)
                    },
                    popBackStack = navController::popBackStack
                )
            }
        ) { paddingValues ->
            NavigationHost(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                navController = navController
            )
        }

        PlayerInit(
            swipeableState = swipeState
        )
    }
}

@Composable
@Preview
fun MainScreenV2Preview() {
    AppTheme {
        MainScreenV2()
    }
}