package com.fagundes.myshowlist.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path

fun provideJikanHttpClient(): HttpClient =
    baseHttpClient().config {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.jikan.moe"
                path("v4")
            }
        }
    }