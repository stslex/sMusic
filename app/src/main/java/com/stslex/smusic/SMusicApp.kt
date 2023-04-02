package com.stslex.smusic

import android.app.Application
import com.stslex.core.datastore.appDataStoreModule
import com.stslex.core.network.di.networkModule
import com.stslex.feature.home.di.homeModule
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
                networkModule
            )
        }
        super.onCreate()
    }
}