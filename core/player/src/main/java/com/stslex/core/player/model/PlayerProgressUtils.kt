package com.stslex.core.player.model

import java.util.concurrent.TimeUnit

object PlayerProgressUtils {

    private const val DEFAULT_TIME = "00:00"

    fun progressPercentage(
        progress: Long,
        duration: Long
    ): Float = if (progress == 0L || duration == 0L) {
        0f
    } else {
        progress.toFloat() / duration.toFloat()
    }

    fun getTime(time: Long): String {
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