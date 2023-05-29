package com.stslex.core.player.service

import android.content.Intent
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.stslex.core.player.session.AppMediaSession
import com.stslex.core.player.player.AppPlayer
import com.stslex.core.player.notification.manager.MediaNotificationManager
import org.koin.android.ext.android.inject

class MediaService : MediaSessionService() {

    private val mediaSession: AppMediaSession by inject()
    private val exoPlayer: AppPlayer by inject()
    private val notificationManager: MediaNotificationManager by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession.mediaSession
        )
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession.mediaSession

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        exoPlayer.release()
    }
}
