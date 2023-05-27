package com.stslex.smusic.di

import android.app.PendingIntent
import android.content.Intent
import com.stslex.core.player.notification.manager.MediaNotificationManager.Companion.PENDING_QUALIFIER
import com.stslex.smusic.MainActivity
import com.stslex.smusic.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainActivityViewModel)
    single<PendingIntent>(qualifier = named(PENDING_QUALIFIER)) {
        val intent = Intent(androidApplication(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        PendingIntent.getActivity(
            androidApplication(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
    }
}
