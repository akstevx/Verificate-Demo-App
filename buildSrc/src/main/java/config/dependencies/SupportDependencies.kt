package config.dependencies

import config.Versions

object SupportDependencies {
    internal const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    internal const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    internal const val material_design = "com.google.android.material:material:${Versions.materialDesignVersion}"
    internal const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layoutVersion}"
    internal const val legacy_support = "androidx.legacy:legacy-support-v4:${Versions.supportVersion}"

}