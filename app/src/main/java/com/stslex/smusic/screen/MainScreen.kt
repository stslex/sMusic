package com.stslex.smusic.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stslex.core.player.model.PlayerEvent
import com.stslex.core.player.model.PlayerPlayingState
import com.stslex.core.player.model.SimpleMediaState
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navToSettings
import com.stslex.smusic.screen.appbar.AppTopAppBar
import com.stslex.smusic.screen.player.PlayerContainer
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    currentMediaItem: KProperty0<Flow<MediaItem?>>,
    playerPlayingState: KProperty0<Flow<PlayerPlayingState>>,
    playerPlayingProgress: KProperty0<Flow<SimpleMediaState.Progress>>,
    onPlayerClick: (PlayerEvent) -> Unit
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
        },
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues.animatePaddingValues())
        ) {
            NavigationHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = animatedBackground().value),
                navController = navHostController
            )
            PlayerContainer(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentMediaItem = currentMediaItem,
                onPlayerClick = onPlayerClick,
                playerPlayingState = playerPlayingState,
                playerPlayingProgress = playerPlayingProgress
            )
        }
    }
}
