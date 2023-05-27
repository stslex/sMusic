package com.stslex.core.player.model

import java.util.concurrent.TimeUnit

sealed class SimpleMediaState {

    object Initial : SimpleMediaState()

    data class Ready(val duration: Long) : SimpleMediaState()

    data class Progress(
        private val progress: Long = 0L,
        val duration: Long = 0L
    ) : SimpleMediaState() {

        companion object {
            private const val DEFAULT_TIME = "00:00"
        }

        val progressPercentage: Float
            get() = if (progress == 0L || duration == 0L) {
                0f
            } else {
                progress.toFloat() / duration.toFloat()
            }

        val currentProgress: String
            get() = getTime(progress)

        val currentDuration: String
            get() = getTime(duration)

        private fun getTime(time: Long): String {
            if (time <= 0) {
                return DEFAULT_TIME
            }

            val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(time) - minutes * 60
            return StringBuilder()
                .append(minutes.convertedTime)
                .append(":")
                .append(seconds.convertedTime)
                .toString()
        }

        private val Long.convertedTime: String
            get() = toString().let { time ->
                if (time.length == 1) {
                    "0$time"
                } else {
                    time
                }
            }
    }

    data class Buffering(val progress: Long) : SimpleMediaState()
    data class Playing(val isPlaying: Boolean) : SimpleMediaState()
}
