package com.stslex.smusic.ui.appbar

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.stslex.core.navigation.NavDestination

@Composable
fun AppBarNavigationIcon(
    modifier: Modifier = Modifier,
    currentRoute: String,
    popBackStack: () -> Unit
) {
    if (
        currentRoute != NavDestination.RECOMMENDATION.route &&
        currentRoute != NavDestination.SEARCH.route &&
        currentRoute != NavDestination.FAVOURITE.route
    ) {
        IconButton(
            modifier = modifier,
            onClick = popBackStack
        ) {
            Image(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "back",
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}