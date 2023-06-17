package com.stslex.smusic.ui.v1.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stslex.core.ui.components.setStaticPlaceHolder
import com.stslex.core.ui.extensions.animatedOnBackground

@Composable
fun PlayerTextContainer(
    modifier: Modifier = Modifier,
    artist: String,
    title: String,
    isShimmerVisible: Boolean
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.CenterStart)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .setStaticPlaceHolder(isVisible = isShimmerVisible),
                text = artist,
                style = MaterialTheme.typography.titleMedium,
                color = animatedOnBackground().value,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .setStaticPlaceHolder(isVisible = isShimmerVisible),
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = animatedOnBackground().value,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}