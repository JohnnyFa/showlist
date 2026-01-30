package com.fagundes.myshowlist.core.data.remote.datasource

import com.fagundes.myshowlist.core.data.remote.api.AnimeApi
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.core.data.mapper.toDomain

class ContentRemoteDataSourceImpl(
    private val movieApi: MovieApi,
    private val animeApi: AnimeApi
) : ContentRemoteDataSource {

    override suspend fun getPopularMovies(): List<Movie> =
        movieApi.getPopularMovies()
            .results
            .map { it.toDomain() }

    override suspend fun getRecommendedMovies(): List<Movie> =
        movieApi.getRecommendedMovies()
            .results
            .map { it.toDomain() }

    override suspend fun getTopAnimes(): List<Anime> =
        animeApi.getTopAnimes()
            .data
            .map { it.toDomain() }

    override suspend fun getShowOfTheDay(): Movie =
        movieApi.getShowOfTheDay()
            .results
            .random()
            .toDomain()

    override suspend fun getMoviesByCategory(category: Int): List<Movie> =
        movieApi.getMoviesByCategory(category)
            .results
            .map { it.toDomain() }

    override suspend fun searchMoviesByName(query: String): List<Movie> =
        movieApi.getMoviesByName(query)
            .results
            .map { it.toDomain() }
}
