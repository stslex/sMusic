package com.stslex.core.player.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

interface AppPlayer {

    val player: ExoPlayer

    val currentPosition: Long

    val duration: Long

    val currentMediaItem: MediaItem?

    val isPlaying: Boolean

    fun addMediaItem(mediaItem: MediaItem)

    fun addMediaItem(index: Int, mediaItem: MediaItem)

    fun addListener(listener: Player.Listener)

    fun prepare()

    fun release()

    fun seekTo(index: Int, progress: Long)

    fun seekTo(position: Long)

    fun play()

    fun playWhenReadyOrNot()

    fun pause()

    fun seek(type: SeekType)
}

