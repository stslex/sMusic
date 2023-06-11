package com.stslex.smusic.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stslex.core.ui.theme.AppTheme
import com.stslex.smusic.ui.v2.MainScreenV2
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel by inject<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isSystemDark = isSystemInDarkTheme()
            val systemUiController = rememberSystemUiController()
            val navHostController = rememberNavController()

            val settingsValue by remember(viewModel) { viewModel.settings }.collectAsState()

            val isDarkTheme by remember {
                derivedStateOf {
                    if (settingsValue.isSystemThemeEnable) {
                        isSystemDark
                    } else {
                        settingsValue.isDarkTheme
                    }
                }
            }

            DisposableEffect(systemUiController, isDarkTheme) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = isDarkTheme.not()
                )
                onDispose { }
            }

            AppTheme(
                isDarkTheme = isDarkTheme
            ) {

                MainScreenV2(
                    navController = navHostController
                )
//                MainScreen(
//                    navHostController = navHostController,
//                    currentMediaItem = viewModel::currentMediaItem,
//                    onPlayerClick = viewModel::onPlayerClick,
//                    simpleMediaState = viewModel::simpleMediaState,
//                )
            }
        }
    }
}
