package com.stslex.core.navigation

enum class NavDestination {
    HOME,
    SETTINGS;

    val route: String = this.name.lowercase()
}