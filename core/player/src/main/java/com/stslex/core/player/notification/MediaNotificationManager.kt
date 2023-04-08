package com.stslex.core.player.notification

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

interface MediaNotificationManager {

    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession
    )
}