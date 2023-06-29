package com.stslex.smusic.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalView
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner

@Composable
fun BlurCard(
    modifier: Modifier = Modifier
) {
    MyComposeView(LocalView.current).show()
}

class MyComposeView(composeView: View) : AbstractComposeView(composeView.context) {

    init {
        ViewTreeLifecycleOwner
            .set(this, ViewTreeLifecycleOwner.get(composeView))
        ViewTreeViewModelStoreOwner
            .set(this, ViewTreeViewModelStoreOwner.get(composeView))
        setViewTreeSavedStateRegistryOwner(
            composeView.findViewTreeSavedStateRegistryOwner()
        )
    }

    private val windowManager =
        composeView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var params: WindowManager.LayoutParams =
        WindowManager.LayoutParams().apply {
            gravity = Gravity.TOP
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            token = composeView.applicationWindowToken
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            format = PixelFormat.TRANSLUCENT
            flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        }

    @Composable
    override fun Content() {
        // Some Compose Drawing Function
    }

    fun show() {
        windowManager.addView(this, params)
    }
}