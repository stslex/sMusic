package com.stslex.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class NavigationEndpoint(
    val watchEndpoint: Endpoint.Watch?,
    val watchPlaylistEndpoint: Endpoint.WatchPlaylist?,
    val browseEndpoint: Endpoint.Browse?,
    val searchEndpoint: Endpoint.Search?,
) {
    val endpoint: Endpoint?
        get() = watchEndpoint ?: browseEndpoint ?: watchPlaylistEndpoint ?: searchEndpoint

    @Serializable
    internal sealed class Endpoint {
        @Serializable
        internal data class Watch(
            val params: String? = null,
            val playlistId: String? = null,
            val videoId: String? = null,
            val index: Int? = null,
            val playlistSetVideoId: String? = null,
            val watchEndpointMusicSupportedConfigs: WatchEndpointMusicSupportedConfigs? = null,
        ) : Endpoint() {
            val type: String?
                get() = watchEndpointMusicSupportedConfigs
                    ?.watchEndpointMusicConfig
                    ?.musicVideoType

            @Serializable
            internal data class WatchEndpointMusicSupportedConfigs(
                val watchEndpointMusicConfig: WatchEndpointMusicConfig?
            ) {

                @Serializable
                internal data class WatchEndpointMusicConfig(
                    val musicVideoType: String?
                )
            }
        }

        @Serializable
        internal data class WatchPlaylist(
            val params: String?,
            val playlistId: String?,
        ) : Endpoint()

        @Serializable
        internal data class Browse(
            val params: String? = null,
            val browseId: String? = null,
            val browseEndpointContextSupportedConfigs: BrowseEndpointContextSupportedConfigs? = null,
        ) : Endpoint() {
            val type: String?
                get() = browseEndpointContextSupportedConfigs
                    ?.browseEndpointContextMusicConfig
                    ?.pageType

            @Serializable
            internal data class BrowseEndpointContextSupportedConfigs(
                val browseEndpointContextMusicConfig: BrowseEndpointContextMusicConfig
            ) {

                @Serializable
                internal data class BrowseEndpointContextMusicConfig(
                    val pageType: String
                )
            }
        }

        @Serializable
        internal data class Search(
            val params: String?,
            val query: String,
        ) : Endpoint()
    }
}