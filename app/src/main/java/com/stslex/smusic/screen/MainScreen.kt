package com.stslex.smusic.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navToSettings
import com.stslex.smusic.screen.appbar.AppTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val currentDestination = navHostController.currentBackStackEntryAsState()

    val currentRoute = remember {
        derivedStateOf { currentDestination.value?.destination?.route.orEmpty() }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = animatedBackground().value),
        topBar = {
            AppTopAppBar(
                currentRoute = currentRoute.value,
                navToSettings = navHostController::navToSettings,
                popBackStack = navHostController::popBackStack
            )
        }
    ) { paddingValues ->
        NavigationHost(
            modifier = Modifier
                .background(color = animatedBackground().value)
                .padding(paddingValues.animatePaddingValues())
                .fillMaxSize(),
            navController = navHostController
        )
    }
}