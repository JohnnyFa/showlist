package com.fagundes.myshowlist.core.network

import com.fagundes.myshowlist.BuildConfig
import com.fagundes.myshowlist.core.language.currentTmdbLanguage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path

fun provideTmdbHttpClient(): HttpClient =
    baseHttpClient().config {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                path("3")

                parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                parameters.append("language", currentTmdbLanguage())
            }
        }
    }