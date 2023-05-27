package com.stslex.core.player.di

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.controller.MediaServiceControllerImpl
import com.stslex.core.player.image_loader.AppImageLoader
import com.stslex.core.player.image_loader.AppImageLoaderImpl
import com.stslex.core.player.notification.adapter.MediaNotificationAdapterFactory
import com.stslex.core.player.notification.adapter.MediaNotificationAdapterFactoryImpl
import com.stslex.core.player.notification.manager.MediaNotificationManager
import com.stslex.core.player.notification.manager.MediaNotificationManager.Companion.PENDING_QUALIFIER
import com.stslex.core.player.notification.manager.MediaNotificationManagerImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val corePlayerModule = module {

    single<ExoPlayer> {
        ExoPlayer.Builder(androidApplication())
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
            .build()
    }

    single<MediaSession> {
        MediaSession
            .Builder(androidApplication(), get<ExoPlayer>())
            .setSessionActivity(get(named(PENDING_QUALIFIER)))
            .build()
    }

    single<MediaNotificationManager> {
        MediaNotificationManagerImpl(
            context = androidApplication(),
            player = get<ExoPlayer>(),
            notificationFactory = get<MediaNotificationAdapterFactory>()
        )
    }

    singleOf(::MediaNotificationAdapterFactoryImpl) {
        bind<MediaNotificationAdapterFactory>()
    }

    singleOf(::MediaServiceControllerImpl) { bind<MediaServiceController>() }

    single<AppImageLoader> {
        AppImageLoaderImpl(androidApplication())
    }
}