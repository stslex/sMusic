package com.stslex.core.player.model

sealed class SimpleMediaState {

    object Initial : SimpleMediaState()

    data class Ready(val duration: Long) : SimpleMediaState()

    data class Progress(
        val progress: Long = 0L,
        val duration: Long = 0L
    ) : SimpleMediaState() {

        val progressPercentage: Float
            get() = if (progress == 0L || duration == 0L) {
                0f
            } else {
                progress.toFloat() / duration.toFloat()
            }
    }

    data class Buffering(val progress: Long) : SimpleMediaState()
    data class Playing(val isPlaying: Boolean) : SimpleMediaState()
}
