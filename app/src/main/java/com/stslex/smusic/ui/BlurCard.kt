package com.stslex.smusic.ui

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.extensions.roundToPx

@Composable
fun BlurView(
    window: Window,
    centerOffset: DpOffset,
    content: @Composable BoxScope.() -> Unit,
) {
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val view = LocalView.current

    DimensionSubComposeLayout(
        mainContent = {
            Box(
                modifier = Modifier.wrapContentSize(),
            ) {
                content()
            }
        }
    ) { viewSizeDp ->

        val viewSize = IntSize(
            width = viewSizeDp.width.roundToPx,
            height = viewSizeDp.height.roundToPx
        )

        val offsetDp = DpOffset(
            x = centerOffset.x - viewSizeDp.width / 2,
            y = centerOffset.y - viewSizeDp.height / 2
        )

        val offset = IntOffset(
            x = offsetDp.x.roundToPx,
            y = offsetDp.y.roundToPx
        )

        if (view.width > 0 && view.height > 0) {
            captureView(
                size = viewSize,
                offset = offset,
                window = window,
            ) { calculatedBitmap ->
                bitmap = calculatedBitmap
            }
        }

        bitmap?.let { currentBitmap ->
            Box(
                Modifier
                    .size(viewSizeDp)
                    .offset { offset }
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .blur(100.dp)
                        .alpha(0.9f)
                        .paint(
                            BitmapPainter(
                                image = currentBitmap.asImageBitmap(),
                                srcSize = viewSize,
                            ),
                        )
                )
                content()
            }
        }
    }
}

private fun captureView(
    size: IntSize,
    offset: IntOffset,
    window: Window,
    bitmapCallback: (Bitmap) -> Unit
) {
    val bitmap = Bitmap.createBitmap(
        size.width,
        size.height,
        Bitmap.Config.ARGB_8888
    )
    PixelCopy.request(
        window,
        Rect(
            offset.x,
            offset.y,
            offset.x + size.width,
            offset.y + size.height
        ),
        bitmap,
        { pixelCopyState ->
            if (pixelCopyState == PixelCopy.SUCCESS) {
                bitmapCallback(bitmap)
            }
        },
        Handler(Looper.getMainLooper()),
    )
}

@Composable
internal fun DimensionSubComposeLayout(
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (DpSize) -> Unit
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints: Constraints ->

        val mainPlaceable: Placeable? = subcompose(SlotsEnum.Main, mainContent)
            .map { measurable ->
                measurable.measure(
                    constraints.copy(
                        minWidth = 0,
                        minHeight = 0
                    )
                )
            }
            .firstOrNull()

        val dependentPlaceable: Placeable? = subcompose(SlotsEnum.Dependent) {
            mainPlaceable?.let { placeable ->
                dependentContent(
                    DpSize(
                        width = placeable.width.toDp(),
                        height = placeable.height.toDp()
                    )
                )
            }
        }
            .map { measurable: Measurable ->
                measurable.measure(constraints)
            }
            .firstOrNull()


        layout(
            width = mainPlaceable?.width ?: 0,
            height = mainPlaceable?.height ?: 0
        ) {
            dependentPlaceable?.placeRelative(0, 0)
        }
    }
}

/**
 * Enum class for SubcomposeLayouts with main and dependent Composables
 */
enum class SlotsEnum { Main, Dependent }

