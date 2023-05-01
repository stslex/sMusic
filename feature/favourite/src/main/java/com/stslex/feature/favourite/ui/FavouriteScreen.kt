package com.stslex.feature.favourite.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.core.navigation.NavigationScreen

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
    viewModel: FavouriteViewModel
) {
    Box(modifier = modifier) {
        Text(text = "favourite")
    }
}