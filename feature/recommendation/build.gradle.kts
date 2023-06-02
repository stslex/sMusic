@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.library")
    id("smusic.android.library.compose")
    kotlin("plugin.serialization") version "1.8.10"
}

android.namespace = "com.stslex.feature.recommendation"

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:network"))
    implementation(project(":core:player"))

    implementation(libs.bundles.koin)
}