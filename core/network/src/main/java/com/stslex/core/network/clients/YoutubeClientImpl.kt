package com.stslex.core.network.clients

import com.stslex.core.network.data.model.YouTubePage
import com.stslex.core.network.mappers.mapYouTubePage
import com.stslex.core.network.model.body.BrowseBody
import com.stslex.core.network.model.body.NextBody
import com.stslex.core.network.model.response.browse.BrowseResponse
import com.stslex.core.network.model.response.next.NextResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class YoutubeClientImpl(
    private val client: HttpClient
) : YoutubeClient {

    companion object {
        private const val next = "/youtubei/v1/next"
        private const val browse = "/youtubei/v1/browse"
    }

    override fun makeNextRequest(
        requestBody: NextBody
    ): Flow<YouTubePage> = flow {
        val nextResponse = client
            .post(next) {
                setBody(requestBody)
            }
            .body<NextResponse>()

        val browseId = nextResponse
            .contents
            ?.singleColumnMusicWatchNextResultsRenderer
            ?.tabbedRenderer
            ?.watchNextTabbedResultsRenderer
            ?.tabs
            ?.getOrNull(2)
            ?.tabRenderer
            ?.endpoint
            ?.browseEndpoint
            ?.browseId
            ?: return@flow

        val result = client
            .post(browse) {
                setBody(BrowseBody(browseId = browseId))
            }
            .body<BrowseResponse>()
            .contents
            ?.sectionListRenderer
            .mapYouTubePage()

        emit(result)
    }
}
