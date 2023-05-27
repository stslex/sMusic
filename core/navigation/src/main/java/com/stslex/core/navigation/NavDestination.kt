package com.stslex.core.navigation

import androidx.annotation.StringRes

enum class NavDestination(
    @StringRes val titleRes: Int?
) {
    RECOMMENDATION(titleRes = R.string.app_nav_recommendation),
    SETTINGS(titleRes = R.string.app_nav_settings),
    FAVOURITE(titleRes = R.string.app_nav_favourite),
    SEARCH(R.string.app_nav_search),
    BACK(titleRes = null),
    PLAYER(titleRes = null);

    val route: String = this.name.lowercase()

    companion object {

        fun valueOfRoute(
            value: String
        ): NavDestination = values()
            .firstOrNull { destination ->
                destination.route == value
            }
            ?: RECOMMENDATION
    }
}