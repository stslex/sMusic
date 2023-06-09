package com.stslex.smusic.ui.appbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stslex.core.navigation.NavDestination

@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    currentRoute: String
) {
    val titleRes = NavDestination.valueOfRoute(currentRoute).titleRes ?: return
    Text(
        modifier = modifier,
        text = stringResource(id = titleRes),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.displaySmall
    )
}