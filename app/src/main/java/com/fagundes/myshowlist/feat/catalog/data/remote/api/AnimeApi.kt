package com.fagundes.myshowlist.feat.catalog.data.remote.api

import com.fagundes.myshowlist.feat.catalog.data.remote.response.JikanResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AnimeApi(
    private val client: HttpClient
) {
    suspend fun getTopAnimes(): JikanResponse {
        return client
            .get("https://api.jikan.moe/v4/top/anime")
            .body()
    }
}
