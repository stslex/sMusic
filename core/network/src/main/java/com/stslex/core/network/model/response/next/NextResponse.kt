package com.stslex.core.network.model.response.next

import com.stslex.core.network.model.response.Continuation
import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.PlaylistPanelVideoRenderer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
internal data class NextResponse(
    val contents: Contents?
) {
    @Serializable
    internal data class MusicQueueRenderer(
        val content: Content?
    ) {
        @Serializable
        internal data class Content(
            @JsonNames("playlistPanelContinuation")
            val playlistPanelRenderer: PlaylistPanelRenderer?
        ) {
            @Serializable
            internal data class PlaylistPanelRenderer(
                val contents: List<Content>?,
                val continuations: List<Continuation>?,
            ) {
                @Serializable
                internal data class Content(
                    val playlistPanelVideoRenderer: PlaylistPanelVideoRenderer?,
                    val automixPreviewVideoRenderer: AutomixPreviewVideoRenderer?,
                ) {

                    @Serializable
                    internal data class AutomixPreviewVideoRenderer(
                        val content: Content?
                    ) {
                        @Serializable
                        internal data class Content(
                            val automixPlaylistVideoRenderer: AutomixPlaylistVideoRenderer?
                        ) {
                            @Serializable
                            internal data class AutomixPlaylistVideoRenderer(
                                val navigationEndpoint: NavigationEndpoint?
                            )
                        }
                    }
                }
            }
        }
    }

    @Serializable
    internal data class Contents(
        val singleColumnMusicWatchNextResultsRenderer: SingleColumnMusicWatchNextResultsRenderer?
    ) {
        @Serializable
        internal data class SingleColumnMusicWatchNextResultsRenderer(
            val tabbedRenderer: TabbedRenderer?
        ) {
            @Serializable
            internal data class TabbedRenderer(
                val watchNextTabbedResultsRenderer: WatchNextTabbedResultsRenderer?
            ) {
                @Serializable
                internal data class WatchNextTabbedResultsRenderer(
                    val tabs: List<Tab>?
                ) {
                    @Serializable
                    internal data class Tab(
                        val tabRenderer: TabRenderer?
                    ) {
                        @Serializable
                        internal data class TabRenderer(
                            val content: Content?,
                            val endpoint: NavigationEndpoint?,
                            val title: String?
                        ) {
                            @Serializable
                            internal data class Content(
                                val musicQueueRenderer: MusicQueueRenderer?
                            )
                        }
                    }
                }
            }
        }
    }
}