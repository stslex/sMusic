package com.stslex.smusic.ui.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.core.ui.extensions.animatedOnSurface
import com.stslex.core.ui.extensions.animatedSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    navToSettings: () -> Unit,
    popBackStack: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            AppBarTitle(currentRoute = currentRoute)
        },
        actions = {
            AppBarActions(
                currentRoute = currentRoute,
                navToSettings = navToSettings
            )
        },
        navigationIcon = {
            AppBarNavigationIcon(
                currentRoute = currentRoute,
                popBackStack = popBackStack
            )
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