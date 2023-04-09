package com.stslex.core.network.clients

import com.stslex.core.network.data.mappers.mapToData
import com.stslex.core.network.data.model.page.YoutubePageDataModel
import com.stslex.core.network.data.model.player.PlayerDataModel
import com.stslex.core.network.mappers.mapYouTubePage
import com.stslex.core.network.model.body.BrowseBody
import com.stslex.core.network.model.body.NextBody
import com.stslex.core.network.model.body.PlayerBody
import com.stslex.core.network.model.response.browse.BrowseResponse
import com.stslex.core.network.model.response.next.NextResponse
import com.stslex.core.network.model.response.player.PlayerResponse
import com.stslex.core.network.utils.NetworkExt.browseId
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
        private const val player = "/youtubei/v1/player"
    }

    override fun makeNextRequest(
        id: String
    ): Flow<YoutubePageDataModel> = makeNextRequest(
        requestBody = NextBody(videoId = id)
    )

    private fun makeNextRequest(
        requestBody: NextBody
    ): Flow<YoutubePageDataModel> = flow {
        val nextResponse = client
            .post(next) {
                setBody(requestBody)
            }
            .body<NextResponse>()

        val browseBody = BrowseBody(
            browseId = nextResponse.browseId
        )

        val result = client
            .post(browse) {
                setBody(browseBody)
            }
            .body<BrowseResponse>()
            .contents
            ?.sectionListRenderer
            .mapYouTubePage()
            .mapToData()
        emit(result)
    }

    override fun getPlayerData(
        id: String
    ): Flow<PlayerDataModel> = flow {
        getPlayerData(body = PlayerBody(videoId = id))?.let {
            emit(it)
        }
    }

    override suspend fun getPlayerDataRow(
        id: String
    ): PlayerDataModel? = getPlayerData(
        body = PlayerBody(videoId = id)
    )

    private suspend fun getPlayerData(
        body: PlayerBody
    ): PlayerDataModel? {
        val response = client
            .post(player) {
                setBody(body)
            }
            .body<PlayerResponse>()
            .mapToData()

        return if (response.playabilityStatus == "OK") {
            response
        } else {
            null
        }
    }
}
