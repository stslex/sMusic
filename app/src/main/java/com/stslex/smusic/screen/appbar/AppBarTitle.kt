package com.stslex.smusic.screen.appbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stslex.core.navigation.NavDestination
import com.stslex.core.ui.extensions.animatedOnSurface
import com.stslex.smusic.R

@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    currentRoute: String
) {
    Text(
        modifier = modifier,
        text = if (currentRoute == NavDestination.SETTINGS.route) {
            "Settings"
        } else {
            stringResource(id = R.string.app_name)
        },
        color = animatedOnSurface().value,
        style = MaterialTheme.typography.displaySmall
    )
}