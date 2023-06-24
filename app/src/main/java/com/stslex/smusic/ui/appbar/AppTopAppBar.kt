package com.stslex.smusic.ui.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stslex.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    navToSettings: () -> Unit,
    popBackStack: () -> Unit
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            AppBarTitle(currentRoute = currentRoute)
        },
        actions = {
            AppBarActions(
                currentRoute = currentRoute,
                navToSettings = navToSettings
            )
        },
        navigationIcon = {
            AppBarNavigationIcon(
                currentRoute = currentRoute,
                popBackStack = popBackStack
            )
        },
    )
}

@Composable
@Preview
fun AppTopAppBarPreview() {
    AppTheme {
        AppTopAppBar(
            currentRoute = "",
            navToSettings = {},
            popBackStack = {}
        )
    }
}