package com.stslex.core.navigation

sealed class NavigationScreen(
    val destination: NavDestination
) {
    object Recommendation : NavigationScreen(NavDestination.RECOMMENDATION)
    object Settings : NavigationScreen(NavDestination.SETTINGS)
    object Favourite : NavigationScreen(NavDestination.FAVOURITE)
    object Search : NavigationScreen(NavDestination.SEARCH)
    object Back : NavigationScreen(NavDestination.BACK)
}