package com.fagundes.myshowlist.core.network

import com.fagundes.myshowlist.core.config.AppEnvironment
import com.fagundes.myshowlist.core.language.currentTmdbLanguage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom

fun provideTmdbHttpClient(
    environment: AppEnvironment,
    tmdbApiKey: String
): HttpClient =
    baseHttpClient(enableLogging = environment.enableLogging).config {
        defaultRequest {
            url {
                takeFrom(environment.baseUrl)
                parameters.append("api_key", tmdbApiKey)
                parameters.append("language", currentTmdbLanguage())
            }
        }
    }
