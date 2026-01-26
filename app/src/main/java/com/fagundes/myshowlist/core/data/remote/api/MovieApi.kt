package com.fagundes.myshowlist.core.data.remote.api

import com.fagundes.myshowlist.BuildConfig
import com.fagundes.myshowlist.core.data.remote.response.TmdbResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApi(
    private val client: HttpClient
) {
    suspend fun getPopularMovies(): TmdbResponse {
        return client
            .get("https://api.themoviedb.org/3/movie/popular") {
                parameter("api_key", BuildConfig.TMDB_API_KEY)
            }
            .body()
    }
}
