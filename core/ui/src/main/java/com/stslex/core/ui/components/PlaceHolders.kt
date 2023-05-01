package com.stslex.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.setStaticPlaceHolder(
    isVisible: Boolean,
): Modifier = this.composed {
    placeholder(
        visible = isVisible,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(5.dp)
    )
}

fun Modifier.setDynamicPlaceHolder(
    isVisible: Boolean,
): Modifier = this.composed {
    placeholder(
        visible = isVisible,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(5.dp),
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = MaterialTheme.colorScheme.onSurfaceVariant,
            animationSpec = PlaceholderDefaults.shimmerAnimationSpec
        ),
    )
}