package com.stslex.smusic.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.core.ui.extensions.animatedOnSurface
import com.stslex.core.ui.extensions.animatedSurface
import com.stslex.smusic.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeChangeClicked: (Boolean) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = animatedBackground().value
            ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = animatedOnSurface().value,
                        style = MaterialTheme.typography.displayMedium
                    )
                },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = animatedSurface().value,
                    scrolledContainerColor = animatedSurface().value,
                    navigationIconContentColor = animatedOnSurface().value,
                    titleContentColor = animatedOnSurface().value,
                    actionIconContentColor = animatedOnSurface().value,
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = animatedBackground().value
                )
        ) {

            Switch(
                modifier = Modifier.align(Alignment.Center),
                checked = isDarkTheme,
                onCheckedChange = onThemeChangeClicked
            )
        }
    }
}