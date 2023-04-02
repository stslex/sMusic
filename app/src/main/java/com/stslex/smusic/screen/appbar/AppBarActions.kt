package com.stslex.smusic.screen.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.stslex.core.navigation.NavDestination
import com.stslex.core.ui.extensions.animatedOnSurface

@Composable
fun AppBarActions(
    modifier: Modifier = Modifier,
    currentRoute: String,
    navToSettings: () -> Unit
) {
    AnimatedVisibility(
        visible = currentRoute != NavDestination.SETTINGS.route,
        enter = slideInVertically(
            animationSpec = tween(1000)
        ) { -it },
        exit = slideOutVertically(
            animationSpec = tween(1000)
        ) { -it }
    ) {
        IconButton(
            modifier = modifier,
            onClick = navToSettings
        ) {
            Image(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "settings",
                colorFilter = ColorFilter.tint(
                    color = animatedOnSurface().value
                )
            )
        }
    }
}