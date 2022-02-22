package config.dependencies

import config.Versions

object AndroidTestDependencies{
    internal const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinVersion}"
    internal const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
    internal const val espressoCore= "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    internal const val espressoContrib= "androidx.test.espresso:espresso-contrib:${Versions.espressoCoreVersion}"
    internal const val testRunner = "androidx.test:runner:${Versions.testRunnerVersion}"
    internal const val testRules = "androidx.test:rules:${Versions.testRunnerVersion}"
    internal const val textCore_ktx = "androidx.test:core-ktx:${Versions.testCoreVersion}"
    internal const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockkVersion}"
    internal const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentVersion}"
    internal const val androidxTest_ext = "androidx.test.ext:junit-ktx:${Versions.androidxTest_ext_Version}"
    internal const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navComponentsVersion}"
    internal const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}