object Libs {
    object Support {
        const val version = Versions.support
        const val annotations = "androidx.annotation:annotation:$version"
        const val app_compat = "androidx.appcompat:appcompat:$version"
        const val recyclerview = "androidx.recyclerview:recyclerview:$version"
        const val cardview = "androidx.cardview:cardview:$version"
        const val design = "com.google.android.material:material:$version"
        const val v4 = "androidx.legacy:legacy-support-v4:$version"
        const val core_utils = "androidx.legacy:legacy-support-core-utils:$version"
    }

    object Room {
        const val version = Versions.room
        const val room_runtime = "androidx.room:room-ktx:$version"
        const val room_compiler = "androidx.room:room-compiler:$version"
    }

    object Lifecycle {
        const val version = Versions.lifecycle
        const val runtime = "androidx.lifecycle:lifecycle-runtime:$version"
        const val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        const val reactive_streams = "android.arch.lifecycle:reactivestreams:$version"
        const val java8 = "androidx.lifecycle:lifecycle-common-java8:$version"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
    }

    object Navigation {
        const val version = Versions.navigation
        const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui_ktx = "androidx.navigation:navigation-ui-ktx:$version"
        const val dynamic_feature = "androidx.navigation:navigation-ui-ktx:$version"
        const val safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
    }

    object Hilt {
        const val version = Versions.hilt
        const val core = "com.google.dagger:hilt-android:${Versions.android_hilt}"
        const val compilier = "com.google.dagger:hilt-compiler:$version"
        const val gradle = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android_compilier =
            "com.google.dagger:hilt-android-compiler:${Versions.android_hilt}"
    }

    object Kotlin {
        const val version = Versions.kotlin
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val test = "org.jetbrains.kotlin:kotlin-test-junit:$version"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val allopen = "org.jetbrains.kotlin:kotlin-allopen:$version"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
        const val viewmodel_ktx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.kotlin_viewmodel_ktx}"
        const val core_ktx = "androidx.core:core-ktx:${Versions.kotlin_core_ktx}"
        const val lifecycle_livedata_ktx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.kotlin_lifecycle_livedata_ktx}"
    }

    object Common {
        const val kotpref = "com.chibatching.kotpref:kotpref:${Versions.kotPref}"
        const val kotpref_liveData = "com.chibatching.kotpref:livedata-support:${Versions.kotPref}"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val constraint_layout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
        const val junit = "junit:junit:${Versions.junit}"
        const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle}"
        const val input_mask = "com.redmadrobot:input-mask-android:${Versions.input_mask}"
        const val ktlint_gradle = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
        const val kex = "com.github.nightgoat:kextensions:${Versions.kex}"
    }

    object Firebase {
        const val analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebase}"
        const val crashlytics =
            "com.google.firebase:firebase-crashlytics-ktx:${Versions.firebase_crashlytics}"
        const val bom = "com.google.firebase:firebase-bom:27.1.0"
        const val database = "com.google.firebase:firebase-database-ktx:20.0.0"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1"
        const val firestore = "com.google.firebase:firebase-firestore:23.0.1"
        const val auth = "com.google.firebase:firebase-auth"
    }

    object SResult {
        const val sresult = "com.github.Rasalexman.SResult:sresult:${Versions.sresult}"
        const val sresultPresentation =
            "com.github.Rasalexman.SResult:sresultpresentation:${Versions.sresult}"
    }
}