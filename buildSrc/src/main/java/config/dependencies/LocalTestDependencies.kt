package config.dependencies

import config.Versions

object LocalTestDependencies {
    internal const val jupiter_api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter_Version}"
    internal const val jupiter_params = "org.junit.jupiter:junit-jupiter-params:${Versions.junit_jupiter_Version}"
    internal const val jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit_jupiter_Version}"
    internal const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    internal const val junit4 = "junit:junit:${Versions.junit_4_version}"
}