package com.stslex.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.core.navigation.NavigationScreen

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit = {},
    viewModel: SearchScreenViewModel
) {
    Box(modifier = modifier) {
        Text(text = "search")
    }
}