package com.stslex.core.player.notification

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

interface MediaNotificationManager {

    companion object {
        const val PENDING_QUALIFIER = "PENDING_QUALIFIER"
    }

    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession
    )
}