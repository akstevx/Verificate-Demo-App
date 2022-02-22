package config.dependencies

import config.Versions

object BuildDependencies {
    internal const val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    internal const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    internal const val google_services = "com.google.gms:google-services:${Versions.playServicesVersion}"
    internal const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.3.2.0"
    internal const val build_hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion2}"
    internal const val  navigation_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navComponentsVersion}"
    internal const val crashlytics_gradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradleVersion}"
    internal const val google_service = "com.google.gms:google-services:4.3.10"
}