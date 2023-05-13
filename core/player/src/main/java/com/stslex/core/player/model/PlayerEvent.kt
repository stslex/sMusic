package com.stslex.core.player.model

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Next : PlayerEvent()
    object Previous : PlayerEvent()
    object Stop : PlayerEvent()
    object ResumePause : PlayerEvent()
    data class PlayPauseCurrent(val id: String) : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}
