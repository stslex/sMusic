package com.stslex.core.network.data.model.page

data class WatchDataModel(
    val params: String,
    val playlistId: String,
    val videoId: String,
    val index: Int,
    val playlistSetVideoId: String,
    val musicVideoType: String,
    val name: String
)
