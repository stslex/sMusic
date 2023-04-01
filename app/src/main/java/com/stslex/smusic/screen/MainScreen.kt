package com.stslex.smusic.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stslex.core.navigation.NavDestination
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.core.ui.extensions.animatedOnSurface
import com.stslex.core.ui.extensions.animatedSurface
import com.stslex.smusic.R
import com.stslex.smusic.navigation.NavigationHost
import com.stslex.smusic.navigation.navToSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val currentDestination = navHostController.currentBackStackEntryAsState()
    val configuration = LocalConfiguration.current
    val layoutDirection = remember {
        if (configuration.layoutDirection == android.util.LayoutDirection.RTL) {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = animatedBackground().value
            ),
        topBar = {
            AnimatedVisibility(
                visible = currentDestination.value?.destination?.route == NavDestination.HOME.route,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it }
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = animatedOnSurface().value,
                            style = MaterialTheme.typography.displayMedium
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = navHostController::navToSettings
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "settings",
                                colorFilter = ColorFilter.tint(
                                    color = animatedOnSurface().value
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = animatedSurface().value,
                        scrolledContainerColor = animatedSurface().value,
                        navigationIconContentColor = animatedOnSurface().value,
                        titleContentColor = animatedOnSurface().value,
                        actionIconContentColor = animatedOnSurface().value,
                    )
                )
            }
        }
    ) { paddingValues ->
        val startPadding = animateDpAsState(
            targetValue = paddingValues.calculateStartPadding(layoutDirection),
            label = "startPadding"
        )
        val endPadding = animateDpAsState(
            targetValue = paddingValues.calculateEndPadding(layoutDirection),
            label = "endPadding"
        )

        val topPadding = animateDpAsState(
            targetValue = paddingValues.calculateTopPadding(),
            label = "topPadding"
        )

        val bottomPadding = animateDpAsState(
            targetValue = paddingValues.calculateBottomPadding(),
            label = "bottomPadding"
        )
        NavigationHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = startPadding.value,
                    end = endPadding.value,
                    top = topPadding.value,
                    bottom = bottomPadding.value
                ),
            navController = navHostController
        )
    }
}