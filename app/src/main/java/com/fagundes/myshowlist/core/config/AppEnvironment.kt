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

object DevEnvironment : AppEnvironment {
    override val baseUrl: String = "https://api.themoviedb.org/3/"
    override val animeBaseUrl: String = "https://api.jikan.moe/v4/"
    override val envName: String = "dev"
    override val enableLogging: Boolean = true
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = true,
        enableExperimentalCatalog = true
    )
}

object StagingEnvironment : AppEnvironment {
    override val baseUrl: String = "https://api.themoviedb.org/3/"
    override val animeBaseUrl: String = "https://api.jikan.moe/v4/"
    override val envName: String = "staging"
    override val enableLogging: Boolean = true
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = true,
        enableExperimentalCatalog = false
    )
}

object ProdEnvironment : AppEnvironment {
    override val baseUrl: String = "https://api.themoviedb.org/3/"
    override val animeBaseUrl: String = "https://api.jikan.moe/v4/"
    override val envName: String = "prod"
    override val enableLogging: Boolean = false
    override val featureFlags: FeatureFlags = FeatureFlags(
        showDebugTools = false,
        enableExperimentalCatalog = false
    )
}

fun provideEnvironment(env: String): AppEnvironment = when (env.lowercase()) {
    "dev" -> DevEnvironment
    "staging" -> StagingEnvironment
    "prod" -> ProdEnvironment
    else -> error("Unsupported environment: $env")
}
