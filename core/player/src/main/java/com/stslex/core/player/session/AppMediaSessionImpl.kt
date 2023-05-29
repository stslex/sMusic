package com.stslex.core.player.session

import android.app.PendingIntent
import android.content.Context
import androidx.media3.session.MediaSession
import com.stslex.core.player.player.AppPlayer

class AppMediaSessionImpl(
    player: AppPlayer,
    context: Context,
    intent: PendingIntent
) : AppMediaSession {

    override val mediaSession: MediaSession

    init {
        mediaSession = MediaSession.Builder(context, player.player)
            .setSessionActivity(intent)
            .build()
    }

    override fun release() {
        mediaSession.release()
    }
}