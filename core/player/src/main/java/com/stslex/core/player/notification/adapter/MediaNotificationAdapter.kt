package com.stslex.core.player.notification.adapter

import android.app.PendingIntent
import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import com.stslex.core.player.image_loader.AppImageLoader

@OptIn(UnstableApi::class)
class MediaNotificationAdapter(
    private val pendingIntent: PendingIntent?,
    private val imageLoader: AppImageLoader
) : PlayerNotificationManager.MediaDescriptionAdapter {

    override fun getCurrentContentTitle(player: Player): CharSequence =
        player.mediaMetadata.albumTitle?.toString().orEmpty()

    override fun createCurrentContentIntent(player: Player): PendingIntent? = pendingIntent

    override fun getCurrentContentText(player: Player): CharSequence =
        player.mediaMetadata.displayTitle?.toString().orEmpty()

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        imageLoader(
            uri = player.mediaMetadata.artworkUri,
            onLoad = callback::onBitmap
        )

        // TODO add Default image while loading
        return null
    }
}
