package com.stslex.core.player.notification.adapter

import android.app.PendingIntent

interface MediaNotificationAdapterFactory {

    operator fun invoke(intent: PendingIntent?): MediaNotificationAdapter
}

