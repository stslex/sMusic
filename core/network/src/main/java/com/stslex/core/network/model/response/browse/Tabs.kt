package com.stslex.core.network.model.response.browse

import kotlinx.serialization.Serializable

@Serializable
internal data class Tabs(
    val tabs: List<Tab>?
) {
    @Serializable
    internal data class Tab(
        val tabRenderer: TabRenderer?
    ) {
        @Serializable
        internal data class TabRenderer(
            val content: Content?,
            val title: String?,
            val tabIdentifier: String?,
        ) {
            @Serializable
            internal data class Content(
                val sectionListRenderer: SectionListRenderer?,
            )
        }
    }
}