plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.task"
        minSdk = 24
        targetSdk = 34
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
                "proguard-rules.pro",
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.mockito)
    testImplementation(libs.archCoreTesting)
    testImplementation(libs.mockitoKotlin2)
    testImplementation(libs.kotlinxCoroutinesTest)

    // Dagger - Hilt
    implementation(libs.dagger.hiltandroid)
    kapt(libs.dagger.hiltandroidcompiler)
    kapt(libs.dagger.hiltCompiler)
    implementation(libs.dagger.hiltNavigation)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Compose dependencies
    implementation(libs.androidx.navigationcompose)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycleviewmodelcompose)
    implementation(libs.androidx.lifecycleruntimecompose)

    // Retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.sqaure.logger)
    implementation(libs.okhttp3)

    // data store
    implementation(libs.androidx.datastorepreferences)
    implementation(libs.androidx.datastorepreferencescore)

    // data store
    implementation(libs.room.ktx)
    kapt(libs.room.complier)
    implementation(libs.room.paging)

    // / chucker team
    debugImplementation(libs.chuckerteam)
    releaseImplementation(libs.chuckerteam.no.op)

    // coil
    implementation(libs.coil)

    // Local unit tests
    testImplementation(libs.androidx.core)
    testImplementation(libs.junit)
    testImplementation(libs.archCoreTesting)
    testImplementation(libs.kotlinx.coroutines.test.v180)
    testImplementation(libs.truth)
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.okhttp3)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.turbine)
    implementation(libs.turbine)

}
