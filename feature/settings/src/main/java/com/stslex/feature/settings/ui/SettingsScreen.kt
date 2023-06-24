package com.stslex.feature.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stslex.core.datastore.SettingsDbModel
import com.stslex.core.navigation.NavigationScreen

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
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                )
        ) {

            item {
                SettingsItemTheme(
                    settings = settings,
                    updateSettings = viewModel::updateSettings
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@Composable
fun SettingsItemTheme(
    modifier: Modifier = Modifier,
    settings: SettingsDbModel,
    updateSettings: (SettingsDbModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SettingsColumnTitle(text = "Theme")
        Spacer(modifier = Modifier.padding(16.dp))
        SettingsItemSwitch(
            title = "system",
            isChecked = settings.isSystemThemeEnable,
            onCheckedChange = { isChecked ->
                updateSettings(
                    settings.copy(isSystemThemeEnable = isChecked)
                )
            }
        )
        SettingsItemSwitch(
            title = "dark",
            isChecked = settings.isDarkTheme,
            onCheckedChange = { isChecked ->
                updateSettings(
                    settings.copy(isDarkTheme = isChecked)
                )
            },
            isEnable = settings.isSystemThemeEnable.not()
        )
    }
}

@Composable
fun SettingsColumnTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun BoxScope.SettingsItemText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .align(Alignment.CenterStart),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun SettingsItemSwitch(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isEnable: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsItemText(text = title)
        Switch(
            modifier = modifier.align(Alignment.CenterEnd),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = isEnable
        )
    }
}