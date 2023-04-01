package com.stslex.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.ui.extensions.animatedBackground

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = animatedBackground().value
            )
    ) {
        Text(text = "HOME")
    }
}