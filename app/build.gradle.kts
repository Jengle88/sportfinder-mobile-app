plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    // navigation
    alias(libs.plugins.com.google.devtools.ksp)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "ru.riders.sportfinder"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.riders.sportfinder"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // Easy permission
    implementation(libs.easypermissions)

/*    // MapKit
    implementation(libs.mapkit)*/

    // Google Maps Location Services
    implementation(libs.googlemaps.compose)
    implementation(libs.googlemaps.playservices)
    implementation(libs.googlemaps.utils)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.gson)
    implementation(libs.gson.converter)

    // Dagger2
    implementation(libs.dagger2)
    implementation(libs.dagger2.android)
    kapt(libs.dagger2.android.support)
    kapt(libs.dagger2.android.processor)
    kapt(libs.dagger2.compiler)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Navigation
    implementation(libs.compose.destination.core)
    ksp(libs.compose.destination.ksp)

    implementation(libs.material)

    // ConstraintLayout
    implementation(libs.androidx.constraintlayout.compose)

    // Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}