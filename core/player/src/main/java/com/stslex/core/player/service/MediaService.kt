package com.stslex.core.player.service

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.stslex.core.player.notification.MediaNotificationManager
import org.koin.android.ext.android.inject

class MediaService : MediaSessionService() {

    private val mediaSession: MediaSession by inject()
    private val exoPlayer: ExoPlayer by inject()
    private val notificationManager: MediaNotificationManager by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession
        )
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            release()
            if (exoPlayer.playbackState != Player.STATE_IDLE) {
                exoPlayer.release()
            }
        }
    }
}
