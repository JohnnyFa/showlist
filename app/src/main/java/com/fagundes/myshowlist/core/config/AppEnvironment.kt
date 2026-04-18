package com.fagundes.myshowlist.core.config

interface AppEnvironment {
    val baseUrl: String
    val animeBaseUrl: String
    val envName: String
    val enableLogging: Boolean
    val featureFlags: FeatureFlags
}

data class FeatureFlags(
    val showDebugTools: Boolean = false,
    val enableExperimentalCatalog: Boolean = false
)

data class DevEnvironment(
    override val baseUrl: String,
    override val animeBaseUrl: String
) : AppEnvironment {
    override val envName: String = "dev"
    override val enableLogging: Boolean = true
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = true,
        enableExperimentalCatalog = true
    )
}

data class StagingEnvironment(
    override val baseUrl: String,
    override val animeBaseUrl: String
) : AppEnvironment {
    override val envName: String = "staging"
    override val enableLogging: Boolean = true
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = true,
        enableExperimentalCatalog = false
    )
}

data class ProdEnvironment(
    override val baseUrl: String,
    override val animeBaseUrl: String
) : AppEnvironment {
    override val envName: String = "prod"
    override val enableLogging: Boolean = false
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = false,
        enableExperimentalCatalog = false
    )
}

fun provideEnvironment(
    env: String,
    tmdbBaseUrl: String,
    jikanBaseUrl: String
): AppEnvironment = when (env.lowercase()) {
    "dev" -> DevEnvironment(baseUrl = tmdbBaseUrl, animeBaseUrl = jikanBaseUrl)
    "staging" -> StagingEnvironment(baseUrl = tmdbBaseUrl, animeBaseUrl = jikanBaseUrl)
    "prod" -> ProdEnvironment(baseUrl = tmdbBaseUrl, animeBaseUrl = jikanBaseUrl)
    else -> error("Unsupported environment: $env")
}
