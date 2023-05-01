package com.stslex.smusic.screen.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.stslex.core.navigation.NavDestination
import com.stslex.core.ui.extensions.animatedOnSurface

@Composable
fun AppBarNavigationIcon(
    modifier: Modifier = Modifier,
    currentRoute: String,
    popBackStack: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = currentRoute != NavDestination.RECOMMENDATION.route &&
                currentRoute != NavDestination.SEARCH.route &&
                currentRoute != NavDestination.FAVOURITE.route,
        enter = slideInHorizontally(
            animationSpec = tween(300)
        ) { -it },
        exit = slideOutHorizontally(
            animationSpec = tween(300)
        ) { -it }
    ) {
        IconButton(
            onClick = popBackStack
        ) {
            Image(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "back",
                colorFilter = ColorFilter.tint(
                    color = animatedOnSurface().value
                )
            )
        }
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = currentRoute == NavDestination.RECOMMENDATION.route ||
                currentRoute == NavDestination.SEARCH.route ||
                currentRoute == NavDestination.FAVOURITE.route,
        enter = slideInHorizontally(
            animationSpec = tween(300)
        ) { -it },
        exit = slideOutHorizontally(
            animationSpec = tween(300)
        ) { -it }
    ) {
        IconButton(
            onClick = {

            }
        ) {
            Image(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "back",
                colorFilter = ColorFilter.tint(
                    color = animatedOnSurface().value
                )
            )
        }
    }
}