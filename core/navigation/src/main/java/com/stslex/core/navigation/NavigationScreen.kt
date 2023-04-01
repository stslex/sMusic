package com.stslex.core.navigation

sealed interface NavigationScreen {
    object Home : NavigationScreen
    object Settings : NavigationScreen
}