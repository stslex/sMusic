package com.stslex.core.player.model

import androidx.compose.runtime.Stable

@Stable
data class SimpleMediaState(
    val duration: Long = 0L,
    val progress: Long = 0L,
    val isPlaying: Boolean = false,
    val playerState: PlayerState = PlayerState.Initial
) {

    val playerPlayingState: PlayerPlayingState
        get() = if (isPlaying) {
            PlayerPlayingState.PLAY
        } else {
            PlayerPlayingState.PAUSE
        }

    val progressPercentage: Float
        get() = PlayerProgressUtils.progressPercentage(
            progress = progress,
            duration = duration
        )

    val currentProgress: String
        get() = PlayerProgressUtils.getTime(progress)

    val currentDuration: String
        get() = PlayerProgressUtils.getTime(duration)
}
