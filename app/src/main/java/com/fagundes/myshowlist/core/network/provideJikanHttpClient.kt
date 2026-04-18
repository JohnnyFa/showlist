package com.fagundes.myshowlist.core.network

import com.fagundes.myshowlist.core.config.AppEnvironment
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom

fun provideJikanHttpClient(environment: AppEnvironment): HttpClient =
    baseHttpClient(enableLogging = environment.enableLogging).config {
        defaultRequest {
            url {
                takeFrom(environment.animeBaseUrl)
            }
        }
    }
