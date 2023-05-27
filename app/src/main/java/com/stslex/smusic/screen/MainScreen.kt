package com.stslex.smusic.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.navigation.NavigationScreen.Companion.isMainScreen
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.core.ui.extensions.toPx
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navigate
import com.stslex.smusic.screen.appbar.AppTopAppBar
import com.stslex.smusic.screen.bottom_appbar.AppBottomBar
import com.stslex.smusic.screen.player.PlayerContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty0

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    currentMediaItem: KProperty0<StateFlow<MediaItem?>>,
    simpleMediaState: KProperty0<StateFlow<SimpleMediaState>>,
    onPlayerClick: (PlayerEvent) -> Unit
) {
    val currentDestination = navHostController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { currentDestination.value?.destination?.route.orEmpty() }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = animatedBackground().value),
        topBar = {
            AppTopAppBar(
                currentRoute = currentRoute,
                navToSettings = {
                    navHostController.navigate(NavigationScreen.Settings)
                },
                popBackStack = navHostController::popBackStack
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isMainScreen(currentRoute),
                enter = slideInVertically(
                    tween(300)
                ) { it },
                exit = slideOutVertically(
                    tween(300)
                ) { it }
            ) {
                AppBottomBar(
                    onBottomAppBarClick = navHostController::navigate
                )
            }
        }
    ) { paddingValues ->
        val bottomPadding = paddingValues.calculateBottomPadding().toPx.toInt()

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavigationHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = animatedBackground().value),
                navController = navHostController
            )

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = isMainScreen(currentRoute),
                enter = slideInVertically(
                    tween(300)
                ) { it + bottomPadding },
                exit = slideOutVertically(
                    tween(300)
                ) { it + bottomPadding }
            ) {
                PlayerContainer(
                    currentMediaItem = currentMediaItem,
                    onPlayerClick = onPlayerClick,
                    simpleMediaState = simpleMediaState,
                    onContainerClick = remember {
                        { navHostController.navigate(NavigationScreen.Player) }
                    }
                )
            }
        }
    }
}
