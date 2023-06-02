@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.library")
    kotlin("plugin.serialization") version "1.8.10"
}

android.namespace = "com.stslex.core.network"

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.koin)
}