package com.stslex.core.network.di

import com.stslex.core.network.NetworkKtorClient
import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.clients.YoutubeClientImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { NetworkKtorClient.client }
    singleOf(::YoutubeClientImpl) { bind<YoutubeClient>() }
}