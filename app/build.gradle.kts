plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 31
    buildToolsVersion = Builds.BUILD_TOOLS

    defaultConfig {
        applicationId = Builds.APP_ID
        minSdk = Builds.MIN_VERSION
        targetSdk = Builds.TARGET_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(Builds.Types.RELEASE) {
            isMinifyEnabled = false
        }
        getByName(Builds.Types.DEBUG) {
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

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        languageVersion = "1.5"
        apiVersion = "1.5"
    }
}

kapt {
    correctErrorTypes = true
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    implementation(Libs.Support.app_compat)
    implementation(Libs.Support.design)

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.Kotlin.coroutinesAndroid)
    implementation(Libs.Kotlin.viewmodel_ktx)
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Kotlin.lifecycle_livedata_ktx)

    kapt(Libs.Hilt.android_compilier)
    implementation(Libs.Hilt.core)

    implementation(Libs.Lifecycle.runtime)
    implementation(Libs.Lifecycle.runtime_ktx)
    implementation(Libs.Lifecycle.extensions)

    implementation(Libs.Firebase.analytics)
    implementation(Libs.Firebase.crashlytics)
    implementation(Libs.Firebase.auth)
    implementation(platform(Libs.Firebase.bom))
    implementation(Libs.Firebase.coroutines)
    implementation(Libs.Firebase.database)
    implementation(Libs.Firebase.firestore)

    implementation(Libs.Common.kotpref)
    implementation(Libs.Common.kotpref_liveData)
    implementation(Libs.Common.timber)
    implementation(Libs.Common.kex)

    implementation(Libs.Room.room_runtime)
    kapt(Libs.Room.room_compiler)

    implementation(Libs.Common.input_mask)

    implementation(Libs.Navigation.fragment_ktx)
    implementation(Libs.Navigation.ui_ktx)

    implementation(Libs.SResult.sresult)
    implementation(Libs.SResult.sresultPresentation)
}

apply(plugin = "com.google.gms.google-services")
