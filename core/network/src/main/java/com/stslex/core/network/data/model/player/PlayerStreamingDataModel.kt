package com.stslex.core.network.data.model.player

data class PlayerStreamingDataModel(
    val adaptiveFormats: List<PlayerAdaptiveFormat>
) {

    companion object {
        private const val QUALITY_NORMAL = 251
        private const val QUALITY_DEFAULT = 140
    }

    val highestQualityFormat: PlayerAdaptiveFormat?
        get() = adaptiveFormats
            .lastOrNull { format ->
                format.itag == QUALITY_NORMAL || format.itag == QUALITY_DEFAULT
            }
            ?: adaptiveFormats.firstOrNull()
}
