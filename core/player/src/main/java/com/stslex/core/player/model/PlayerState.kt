package com.stslex.core.player.model

import androidx.compose.runtime.Stable

@Stable
sealed interface PlayerState {

    @Stable
    object Initial : PlayerState

    @Stable
    object Content : PlayerState

    @Stable
    data class Buffering(
        val progress: Long
    ) : PlayerState
}
