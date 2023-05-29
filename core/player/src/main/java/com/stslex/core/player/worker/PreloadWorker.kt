package com.stslex.core.player.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultHttpDataSource
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

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun doWork(): Result {
        try {
            val cache: SimpleCache = AppPlayerImpl.cache ?: return Result.failure()

            val videoUrl: String? = inputData.getString(AUDIO_URL)
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)


            val cacheDataSource = CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .createDataSource()

            preCacheAudio(videoUrl, cacheDataSource)

            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun preCacheAudio(
        audioUrl: String?,
        cacheDataSource: CacheDataSource
    ) {
        val dataSpec = DataSpec(Uri.parse(audioUrl))

        val progressListener = CacheWriter.ProgressListener { requestLength, bytesCached, _ ->
            if (requestLength == bytesCached) {
                Log.i("PreloadWorker", "cached: $audioUrl")
            }
        }

        cachingJob = GlobalScope.launch(Dispatchers.IO) {
            cache(dataSpec, cacheDataSource, progressListener)
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun cache(
        dataSpec: DataSpec,
        cacheDataSource: CacheDataSource,
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