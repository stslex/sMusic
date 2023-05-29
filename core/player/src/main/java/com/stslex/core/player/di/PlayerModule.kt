package com.stslex.core.player.di

import com.stslex.core.player.controller.MediaServiceController
import com.stslex.core.player.controller.MediaServiceControllerImpl
import com.stslex.core.player.image_loader.AppImageLoader
import com.stslex.core.player.image_loader.AppImageLoaderImpl
import com.stslex.core.player.notification.adapter.MediaNotificationAdapterFactory
import com.stslex.core.player.notification.adapter.MediaNotificationAdapterFactoryImpl
import com.stslex.core.player.notification.manager.MediaNotificationManager
import com.stslex.core.player.notification.manager.MediaNotificationManager.Companion.PENDING_QUALIFIER
import com.stslex.core.player.notification.manager.MediaNotificationManagerImpl
import com.stslex.core.player.player.AppPlayer
import com.stslex.core.player.player.AppPlayerImpl
import com.stslex.core.player.session.AppMediaSession
import com.stslex.core.player.session.AppMediaSessionImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val corePlayerModule = module {

    single<AppPlayer> {
        AppPlayerImpl(androidApplication())
    }

    single<AppMediaSession> {
        AppMediaSessionImpl(
            context = androidApplication(),
            player = get<AppPlayer>(),
            intent = get(named(PENDING_QUALIFIER))
        )
    }

    single<MediaNotificationManager> {
        MediaNotificationManagerImpl(
            context = androidApplication(),
            player = get<AppPlayer>(),
            notificationFactory = get<MediaNotificationAdapterFactory>()
        )
    }

    singleOf(::MediaNotificationAdapterFactoryImpl) {
        bind<MediaNotificationAdapterFactory>()
    }

    single<MediaServiceController> {
        MediaServiceControllerImpl(
            player = get<AppPlayer>(),
            context = androidApplication()
        )
    }

    single<AppImageLoader> {
        AppImageLoaderImpl(androidApplication())
    }
}