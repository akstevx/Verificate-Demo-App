package config.dependencies

import config.Versions


//kapt
object AnnotationProcessingDependencies {
    const val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltCompiler2 = "androidx.hilt:hilt-compiler:1.0.0"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
}