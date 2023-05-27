package com.stslex.smusic.screen.bottom_appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.stslex.core.navigation.NavigationScreen

enum class BottomAppbarItem(
    val navDestination: NavigationScreen,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
) {
    RECOMMENDATION(
        navDestination = NavigationScreen.Recommendation,
        unselectedIcon = Icons.Outlined.Star,
        selectedIcon = Icons.Filled.Star,
    ),
    SEARCH(
        navDestination = NavigationScreen.Search,
        unselectedIcon = Icons.Outlined.Search,
        selectedIcon = Icons.Filled.Search,
    ),
    FAVOURITE(
        navDestination = NavigationScreen.Favourite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
    );

    companion object {

        fun getByRoute(
            route: String
        ): BottomAppbarItem? = BottomAppbarItem.values()
            .firstOrNull { item ->
                item.navDestination.destination.route == route
            }
    }
}