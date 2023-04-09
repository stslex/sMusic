package com.stslex.core.player.model

import androidx.annotation.DrawableRes
import com.stslex.core.player.R

enum class PlayerPlayingState(
    @DrawableRes val res: Int
) {

    PLAY(R.drawable.baseline_play_arrow_24),

    PAUSE(R.drawable.baseline_pause_24);

    val playingRes: Int
        get() = when (this) {
            PLAY -> PAUSE
            PAUSE -> PLAY
        }.res
}
