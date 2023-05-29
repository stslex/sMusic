package com.stslex.core.player.player

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import java.io.File

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class AppPlayerImpl(
    private val context: Context
) : AppPlayer {

    companion object {
        private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
        private const val cacheSize: Long = 100 * 1024 * 1024

        var cache: SimpleCache? = null
            private set
    }

    override val player: ExoPlayer
    private val cacheDataSourceFactory: CacheDataSource.Factory

    override val currentMediaItem: MediaItem?
        get() = player.currentMediaItem

    override val currentPosition: Long
        get() = player.currentPosition

    override val duration: Long
        get() = player.duration

    override val isPlaying: Boolean
        get() = player.isPlaying

    private val simpleCache: SimpleCache

    init {
        simpleCache = initCache()
        cacheDataSourceFactory = getCacheDataSourceFactory()
        val mediaSourceFactory = DefaultMediaSourceFactory(cacheDataSourceFactory)
            .setLoadErrorHandlingPolicy(PlayerHandler)

        player = getPlayer(mediaSourceFactory)
    }

    private fun initCache(): SimpleCache = if (cache == null) {
        val cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        val downloadContentDirectory = File(
            context.getExternalFilesDir(null),
            DOWNLOAD_CONTENT_DIRECTORY
        )
        val exoplayerDatabaseProvider = StandaloneDatabaseProvider(context)
        cache = SimpleCache(
            downloadContentDirectory,
            cacheEvictor,
            exoplayerDatabaseProvider
        )
        requireNotNull(cache)
    } else {
        requireNotNull(cache)
    }

    private fun getCacheDataSourceFactory(): CacheDataSource.Factory {
        val cacheSink = CacheDataSink.Factory().setCache(simpleCache)
        val downStreamFactory = FileDataSource.Factory()
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        val upstreamFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)

        return CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(cacheSink)
            .setCacheReadDataSourceFactory(downStreamFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    private fun getPlayer(
        mediaSourceFactory: DefaultMediaSourceFactory
    ) = ExoPlayer.Builder(context)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .build(),
            true
        )
        .setMediaSourceFactory(mediaSourceFactory)
        .build()

    override fun addMediaItem(mediaItem: MediaItem) {
        player.addMediaSource(mediaItem.mediaSource)
    }

    override fun addMediaItem(index: Int, mediaItem: MediaItem) {
        player.addMediaSource(index, mediaItem.mediaSource)
    }

    private val MediaItem.mediaSource: ProgressiveMediaSource
        get() = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
            .createMediaSource(this)

    override fun prepare() {
        player.prepare()
    }

    override fun addListener(listener: Player.Listener) {
        player.addListener(listener)
    }

    override fun release() {
        if (player.playbackState != Player.STATE_IDLE) {
            player.release()
        }
    }

    override fun seekTo(index: Int, progress: Long) {
        player.seekTo(index, progress)
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun play() {
        player.play()
    }

    override fun playWhenReadyOrNot() {
        player.playWhenReady = player.playWhenReady.not()
    }

    override fun pause() {
        player.pause()
    }

    override fun seek(
        type: SeekType
    ) {
        when (type) {
            SeekType.BACK -> player.seekBack()
            SeekType.FORWARD -> player.seekForward()
            SeekType.NEXT -> player.seekToNext()
            SeekType.PREVIOUS -> player.seekToPrevious()
        }
    }
}