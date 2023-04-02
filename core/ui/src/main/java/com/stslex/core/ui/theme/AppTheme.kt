package com.stslex.core.ui.theme

import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSupportDynamicTheme) {
        LocalContext.current.currentDynamicColorScheme(isDarkTheme)
    } else {
        currentColorScheme(isDarkTheme)
    }

    MaterialTheme(
        shapes = Shapes(),
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

private val currentColorScheme: (isDarkTheme: Boolean) -> ColorScheme
    get() = { isDarkTheme ->
        if (isDarkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }
    }

private val Context.currentDynamicColorScheme: (isDarkTheme: Boolean) -> ColorScheme
    get() = { isDarkTheme ->
        if (isDarkTheme) {
            dynamicDarkColorScheme(this)
        } else {
            dynamicLightColorScheme(this)
        }
    }

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private val isSupportDynamicTheme: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S