@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.library")
}

android.namespace = "com.stslex.core.player"

dependencies {
    implementation(project(":core:network"))

    api(libs.bundles.media3)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.bundles.koin)
    implementation(libs.compose.coil)
}