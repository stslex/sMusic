package com.stslex.feature.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stslex.core.navigation.NavigationScreen
import com.stslex.core.ui.extensions.animatedBackground

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigate: (NavigationScreen) -> Unit,
    viewModel: SettingsViewModel
) {
    val settings by remember(viewModel) {
        viewModel.settings
    }.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = animatedBackground().value
            )
    ) {

        Column {
            Text(text = "system")

            Switch(
                modifier = Modifier.padding(start = 16.dp),
                checked = settings.isSystemThemeEnable,
                onCheckedChange = { isChecked ->
                    viewModel.updateSystemTheme(
                        settings.copy(isSystemThemeEnable = isChecked)
                    )
                }
            )

            Spacer(modifier = Modifier.padding(32.dp))
            Text(text = "dark")

            Switch(
                modifier = Modifier.padding(start = 16.dp),
                checked = settings.isDarkTheme,
                onCheckedChange = { isChecked ->
                    viewModel.updateDarkTheme(
                        settings.copy(isDarkTheme = isChecked)
                    )
                },
                enabled = settings.isSystemThemeEnable.not()
            )
        }

    }
}