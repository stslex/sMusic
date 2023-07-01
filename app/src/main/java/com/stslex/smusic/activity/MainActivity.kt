package com.stslex.smusic.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stslex.core.ui.theme.AppTheme
import com.stslex.feature.player.navigation.PlayerInit
import com.stslex.feature.player.ui.base.AppSwipeState
import com.stslex.feature.player.ui.base.rememberSwipeableState
import com.stslex.smusic.databinding.ActivityMainBinding
import com.stslex.smusic.ui.BlurView
import com.stslex.smusic.ui.MainScreen
import eightbitlab.com.blurview.RenderEffectBlur
import eightbitlab.com.blurview.RenderScriptBlur
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel by inject<MainActivityViewModel>()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = requireNotNull(_binding)


    private val _swipeableState: AppSwipeState? = null
    private val swipeableState: AppSwipeState
        @Composable
        get() = _swipeableState ?: rememberSwipeableState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentComposeView.apply {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)

            setContent {
                val isSystemDark = isSystemInDarkTheme()
                val systemUiController = rememberSystemUiController()
                val navHostController = rememberNavController()

                val settingsValue by remember(viewModel) {
                    viewModel.settings
                }.collectAsState()

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

                    MainScreen(
                        navController = navHostController,
                        swipeableState = swipeableState
                    )
                }
            }
        }

        binding.composeViewInsideBlurView.apply {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            setContent {
                PlayerInit(swipeableState = swipeableState)
            }
        }

        binding.blurView
            .setupWith(
                window.decorView as ViewGroup,
                RenderEffectBlur()
            )
            .setFrameClearDrawable(window.decorView.background)
            .setBlurRadius(16f)
    }

    @Composable
    fun ExampleBlur() {
        BlurView(
            window = window,
            centerOffset = DpOffset(
                x = LocalConfiguration.current.screenWidthDp.dp / 2,
                y = LocalConfiguration.current.screenHeightDp.dp / 2
            )
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "TEST BLUR",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}
