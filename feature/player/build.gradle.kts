@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.library")
    id("smusic.android.library.compose")
}

android.namespace = "com.stslex.feature.player"

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:player"))
    implementation("androidx.palette:palette:1.0.0")
}