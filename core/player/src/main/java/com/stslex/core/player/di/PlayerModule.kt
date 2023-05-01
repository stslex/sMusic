package com.stslex.core.player.di

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.controller.MediaServiceControllerImpl
import com.stslex.core.player.notification.MediaNotificationManager
import com.stslex.core.player.notification.MediaNotificationManagerImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val playerModule = module {

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
        MediaSession.Builder(androidApplication(), get<ExoPlayer>()).build()
    }

    single<MediaNotificationManager> {
        MediaNotificationManagerImpl(
            context = androidApplication(),
            player = get<ExoPlayer>()
        )
    }

    singleOf(::MediaServiceControllerImpl) { bind<MediaServiceController>() }
}