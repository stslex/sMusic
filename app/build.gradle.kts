plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.stslex.smusic"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.stslex.smusic"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    kapt {
        correctErrorTypes = true
    }
    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:home"))

    implementation(libs.androidx.activity.ktx)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)
}