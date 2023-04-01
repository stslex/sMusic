plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.stslex.feature.settings"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}