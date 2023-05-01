package com.stslex.smusic

import android.app.Application
import android.content.Intent
import com.stslex.core.datastore.appDataStoreModule
import com.stslex.core.network.di.networkModule
import com.stslex.core.player.di.playerModule
import com.stslex.core.player.service.MediaService
import com.stslex.feature.favourite.di.favouriteModule
import com.stslex.feature.recommendation.di.homeModule
import com.stslex.feature.search.di.searchModule
import com.stslex.feature.settings.di.settingsModule
import com.stslex.smusic.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class SMusicApp : Application() {

    override fun onCreate() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@SMusicApp)
            modules(
                appDataStoreModule,
                settingsModule,
                appModule,
                homeModule,
                networkModule,
                playerModule,
                searchModule,
                favouriteModule
            )
        }
        // TODO only after playing
        startForegroundService(Intent(this, MediaService::class.java))
        super.onCreate()
    }
}