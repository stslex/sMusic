package com.stslex.smusic.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.navigation.PlayerInit
import com.stslex.feature.player.ui.base.rememberSwipeableState
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navigate
import com.stslex.smusic.ui.appbar.AppTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { currentDestination.value?.destination?.route.orEmpty() }
    }
    val swipeableState = rememberSwipeableState()

    BackHandler(
        enabled = swipeableState.swipeProgress >= 0.1f
    ) {
        swipeableState.collapse()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { _ ->
            Column {
                AppTopAppBar(
                    currentRoute = currentRoute,
                    navToSettings = {
                        navController.navigate(NavigationScreen.Settings)
                    },
                    popBackStack = navController::popBackStack
                )
                NavigationHost(
                    modifier = Modifier
                        .weight(1f)
                        .blur(
                            16.dp * swipeableState.swipeProgress
                        )
                        .scale(1 - (swipeableState.swipeProgress * .1f)),
                    navController = navController
                )
            }
        }

        PlayerInit(
            swipeableState = swipeableState
        )
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}