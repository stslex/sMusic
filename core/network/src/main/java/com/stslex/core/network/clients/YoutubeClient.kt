package com.stslex.core.network.clients

import com.stslex.core.network.data.model.YouTubePage
import com.stslex.core.network.model.body.NextBody
import kotlinx.coroutines.flow.Flow

interface YoutubeClient {
    fun makeNextRequest(
        requestBody: NextBody = NextBody(videoId = "J7p4bzqLvCw")
    ): Flow<YouTubePage>
}

