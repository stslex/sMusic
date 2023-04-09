package com.stslex.core.player.model

import com.stslex.core.network.data.model.page.ItemData

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Stop : PlayerEvent()
    object ResumePause : PlayerEvent()
    data class PlayPauseCurrent(
        val songItem: ItemData.SongItem,
        val index: Int
    ) : PlayerEvent()

    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}
