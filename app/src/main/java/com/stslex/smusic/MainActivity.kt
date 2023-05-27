package com.stslex.smusic

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.core.ui.theme.AppTheme
import com.stslex.smusic.screen.MainScreen
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel by inject<MainActivityViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemDark = isSystemInDarkTheme()
            val systemUiController = rememberSystemUiController()
            val navHostController = rememberAnimatedNavController()

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

            AppTheme(
                isDarkTheme = isDarkTheme
            ) {
                systemUiController.setSystemBarsColor(color = animatedBackground().value)

                MainScreen(
                    navHostController = navHostController,
                    currentMediaItem = viewModel::currentMediaItem,
                    onPlayerClick = viewModel::onPlayerClick,
                    simpleMediaState = viewModel::simpleMediaState,
                )
            }
        }
    }
}
