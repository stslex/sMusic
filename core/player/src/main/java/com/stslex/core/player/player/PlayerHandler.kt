package com.stslex.core.player.player

import androidx.media3.common.C
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.exoplayer.upstream.DefaultLoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import java.util.concurrent.TimeUnit

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
object PlayerHandler : DefaultLoadErrorHandlingPolicy() {

    private val DELAY_RETRY_CONNECTION = TimeUnit.SECONDS.toMillis(1)

    override fun getRetryDelayMsFor(
        loadErrorInfo: LoadErrorHandlingPolicy.LoadErrorInfo
    ): Long = if (loadErrorInfo.exception is HttpDataSourceException) {
        DELAY_RETRY_CONNECTION
    } else {
        super.getRetryDelayMsFor(loadErrorInfo)
    }

    override fun getMinimumLoadableRetryCount(
        dataType: Int
    ): Int = if (dataType == C.DATA_TYPE_MEDIA) {
        Int.MAX_VALUE
    } else {
        super.getMinimumLoadableRetryCount(dataType)
    }
}