package com.stslex.core.ui.components.shader.test

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.stslex.core.ui.components.shader.ShaderContainer
import com.stslex.core.ui.components.shader.ShaderItem

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ShaderText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
) {
    val time = 1000

    val blur = remember { Animatable(0f) }

    LaunchedEffect(text) {
        blur.animateTo(30f, tween(time / 2, easing = LinearEasing))
        blur.animateTo(0f, tween(time / 2, easing = LinearEasing))
    }


    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        ShaderContainer(
            modifier = Modifier
                .animateContentSize()
                .clipToBounds()
                .fillMaxWidth(),
            cutoff = .2f
        ) {
            ShaderItem(
                modifier = Modifier.fillMaxWidth(),
                blur = blur.value,
                shaderContent = {
                    AnimatedContent(
                        targetState = text,
                        modifier = Modifier
                            .fillMaxWidth(),
                        transitionSpec = {
                            fadeIn(tween(time, easing = LinearEasing)) + expandVertically(
                                tween(
                                    time,
                                    easing = LinearEasing
                                ), expandFrom = Alignment.CenterVertically
                            ) with fadeOut(
                                tween(
                                    time,
                                    easing = LinearEasing
                                )
                            ) + shrinkVertically(
                                tween(
                                    time,
                                    easing = LinearEasing
                                ), shrinkTowards = Alignment.CenterVertically
                            )
                        },
                        label = "animatable text"
                    ) { text ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = text,
                            style = style,
                            maxLines = maxLines,
                            overflow = overflow,
                            color = color,
                            textAlign = textAlign
                        )
                    }
                }) {}
        }
    }
}