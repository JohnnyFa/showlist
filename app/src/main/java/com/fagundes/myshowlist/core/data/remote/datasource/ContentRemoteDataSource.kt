package com.fagundes.myshowlist.core.data.remote.datasource

import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie

interface ContentRemoteDataSource {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getRecommendedMovies(): List<Movie>
    suspend fun getTopAnimes(): List<Anime>
    suspend fun getShowOfTheDay(): Movie
    suspend fun getMoviesByCategory(category: Int): List<Movie>
    suspend fun searchMoviesByName(query: String): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
}