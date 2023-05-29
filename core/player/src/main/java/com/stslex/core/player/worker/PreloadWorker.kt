package com.stslex.core.player.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheWriter
import androidx.media3.datasource.cache.SimpleCache
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.stslex.core.player.player.AppPlayerImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PreloadWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {

        const val AUDIO_URL = "AUDIO_URL_KEY"

        fun buildWorkRequest(parameter: String): OneTimeWorkRequest {
            val data = Data.Builder()
                .putString(AUDIO_URL, parameter)
                .build()

            return OneTimeWorkRequestBuilder<PreloadWorker>()
                .apply {
                    addTag(parameter)
                    setInputData(data)
                }
                .build()
        }
    }

    private var cachingJob: Job? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSource.Factory
    private lateinit var cacheDataSource: CacheDataSource
    private val cache: SimpleCache = AppPlayerImpl.cache

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun doWork(): Result {
        try {
            val videoUrl: String? = inputData.getString(AUDIO_URL)

            httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)

            defaultDataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)

            cacheDataSource = CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .createDataSource()

            preCacheAudio(videoUrl)

            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun preCacheAudio(
        audioUrl: String?
    ) {
        val dataSpec = DataSpec(Uri.parse(audioUrl))

        val progressListener = CacheWriter.ProgressListener { requestLength, bytesCached, _ ->
            if (requestLength == bytesCached) {
                Log.i("PreloadWorker", "cached: $audioUrl")
            }
        }

        cachingJob = GlobalScope.launch(Dispatchers.IO) {
            cache(dataSpec, progressListener)
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun cache(
        dataSpec: DataSpec,
        progressListener: CacheWriter.ProgressListener
    ) {
        runCatching {
            CacheWriter(
                cacheDataSource,
                dataSpec,
                null,
                progressListener
            )
                .cache()
        }
            .onFailure {
                it.printStackTrace()
            }
    }
}