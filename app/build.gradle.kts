plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.5.31"
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "ru.wanket.exchange_trading_assistant"
        minSdk = 28
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.schemaLocation" to "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.0.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.39.1")
    kapt("com.google.dagger:hilt-android-compiler:2.39.1")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")
    implementation("androidx.compose.ui:ui:1.0.4")
    implementation("androidx.compose.material:material:1.0.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.4")
    implementation("androidx.activity:activity-compose:1.4.0")

    debugImplementation("androidx.compose.ui:ui-tooling:1.0.4")

    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    implementation("androidx.room:room-ktx:2.3.0")

    implementation("androidx.preference:preference-ktx:1.1.1")

    implementation("io.github.vanpra.compose-material-dialogs:core:0.6.1")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.6.1")

    implementation("com.github.madrapps:plot:0.1.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
}

kapt {
    correctErrorTypes = true
}
