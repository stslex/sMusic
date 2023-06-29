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

    val lifecycle_version = "2.6.1"
    val arch_version = "2.2.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")


}