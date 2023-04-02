package com.stslex.core.datastore

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDataStoreModule = module {
    single<SettingsDataStore> {
        SettingsDataStoreImpl(androidContext())
    }
}

