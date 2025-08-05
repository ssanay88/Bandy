plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "suhyeok.yang.bandy"
    compileSdk = 36

    defaultConfig {
        applicationId = "suhyeok.yang.bandy"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

secrets {
    propertiesFileName = "secret.properties"
}

dependencies {
    implementation(project(":feature"))
    implementation(project(":data"))
    implementation(project(":shared"))
    implementation(project(":business"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.libraries)

    // Coil
    implementation(libs.bundles.coil.libraries)

    // Serialization
    implementation(libs.jetbrains.kotlinx.serialization.json)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.libraries)

    // naver Login
    implementation(libs.oauth)

}