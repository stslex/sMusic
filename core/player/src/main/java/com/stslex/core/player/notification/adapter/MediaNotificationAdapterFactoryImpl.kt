package com.stslex.core.player.notification.adapter

import android.app.PendingIntent
import com.stslex.core.player.image_loader.AppImageLoader

class MediaNotificationAdapterFactoryImpl(
    private val imageLoader: AppImageLoader
) : MediaNotificationAdapterFactory {


    override fun invoke(
        intent: PendingIntent?
    ): MediaNotificationAdapter = MediaNotificationAdapter(
        pendingIntent = intent,
        imageLoader = imageLoader
    )
}