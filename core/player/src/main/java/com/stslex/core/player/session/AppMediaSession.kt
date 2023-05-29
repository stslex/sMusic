package com.stslex.core.player.session

import androidx.media3.session.MediaSession

interface AppMediaSession {

    val mediaSession: MediaSession

    fun release()
}

