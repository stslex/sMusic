@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("smusic.android.application")
    id("smusic.android.application.compose")
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:player"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:recommendation"))
    implementation(project(":feature:search"))
    implementation(project(":feature:favourite"))
    implementation(project(":feature:player"))

    implementation(libs.bundles.koin)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)
}