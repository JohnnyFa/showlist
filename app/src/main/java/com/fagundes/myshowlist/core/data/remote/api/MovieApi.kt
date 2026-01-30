package com.fagundes.myshowlist.core.data.remote.api

import com.fagundes.myshowlist.BuildConfig
import com.fagundes.myshowlist.core.data.remote.response.TmdbResponse
import com.fagundes.myshowlist.core.language.currentTmdbLanguage
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
                parameter("language", currentTmdbLanguage())
            }
            .body()
    }

    suspend fun getRecommendedMovies(): TmdbResponse {
        return client
            .get("https://api.themoviedb.org/3/movie/popular") {
                parameter("api_key", BuildConfig.TMDB_API_KEY)
                parameter("language", currentTmdbLanguage())
            }
            .body()
    }

    suspend fun getShowOfTheDay(): TmdbResponse {
        return client
            .get("https://api.themoviedb.org/3/discover/movie") {
                parameter("api_key", BuildConfig.TMDB_API_KEY)
                parameter("language", currentTmdbLanguage())
            }
            .body()
    }
    suspend fun getMoviesByCategory(category: Int): TmdbResponse {
        return client
            .get("https://api.themoviedb.org/3/movie/upcoming") {
                parameter("api_key", BuildConfig.TMDB_API_KEY)
                parameter("language", currentTmdbLanguage())
                parameter("with_genres", category)
            }
            .body()
    }

    suspend fun getMoviesByName(name: String): TmdbResponse {
        return client
            .get("https://api.themoviedb.org/3/search/movie") {
                parameter("api_key", BuildConfig.TMDB_API_KEY)
                parameter("language", currentTmdbLanguage())
                parameter("query", name)
            }
            .body()
    }
}
