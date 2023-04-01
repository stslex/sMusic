package com.stslex.smusic

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stslex.core.ui.extensions.animatedBackground
import com.stslex.smusic.screen.MainScreen
import com.stslex.smusic.theme.AppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemDark = isSystemInDarkTheme()
            val systemUiController = rememberSystemUiController()

            var isDarkTheme by remember { mutableStateOf(isSystemDark) }

            AppTheme(
                isDarkTheme = isDarkTheme
            ) {
                systemUiController.setSystemBarsColor(
                    color = animatedBackground().value
                )

                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeChangeClicked = { isChecked ->
                        isDarkTheme = isChecked
                    }
                )
            }
        }
    }
}