@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.library")
}

android.namespace = "com.stslex.core.datastore"

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.androidx.datastore.preferences)
}