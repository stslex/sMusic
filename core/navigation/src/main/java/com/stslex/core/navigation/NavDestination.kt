package com.stslex.core.navigation

import androidx.annotation.StringRes

enum class NavDestination(
    @StringRes val titleRes: Int
) {
    RECOMMENDATION(
        titleRes = R.string.app_nav_recommendation
    ),

    SETTINGS(
        titleRes = R.string.app_nav_settings
    ),

    FAVOURITE(
        titleRes = R.string.app_nav_favourite
    ),

    SEARCH(
        R.string.app_nav_search
    ),

    BACK(
        R.string.app_name
    );

    val route: String = this.name.lowercase()

    companion object {

        fun valueOfRoute(value: String): NavDestination = when (value) {
            RECOMMENDATION.route -> RECOMMENDATION
            SETTINGS.route -> SETTINGS
            FAVOURITE.route -> FAVOURITE
            SEARCH.route -> SEARCH
            BACK.route -> BACK
            else -> RECOMMENDATION
        }
    }
}