plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.dogapi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dogapi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2" // Use the latest stable version
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation(libs.retrofit)

    // Converter for JSON (Gson)
    implementation(libs.converter.gson)

    // Optional: Logging interceptor for debugging network calls
    implementation(libs.logging.interceptor)

    // Core Kotlin Coroutines library
    implementation(libs.kotlinx.coroutines.core)

    // Coroutines support for Android
    implementation(libs.kotlinx.coroutines.android)

    // Optional: Coroutines support for testing
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinx.coroutines.core)

    // Core Kotlin Coroutines library
    implementation(libs.kotlinx.coroutines.core)

    // Coroutines support for Android
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle ViewModel KTX for viewModelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Lifecycle Runtime KTX (Optional: for lifecycleScope in Activities/Fragments)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose BOM (Bill of Materials) - aligns all Compose library versions
    implementation(platform(libs.androidx.compose.bom))

    // Core Compose libraries
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.androidx.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.ui.tooling)

    // Lifecycle integration for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Optional: Debug tooling
    debugImplementation(libs.androidx.ui.tooling)

    // Optional: Test tooling
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Foundation Library (Required for LazyColumn, LazyRow, etc.)
    implementation(libs.androidx.foundation)

    // Hilt runtime library
    implementation(libs.hilt.android)

    // Hilt compiler for annotation processing
    kapt(libs.hilt.compiler)

    // Hilt Navigation Compose
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation("io.coil-kt:coil-compose:2.2.2")


}