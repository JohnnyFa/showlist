package com.fagundes.myshowlist.feat.catalog.data.repository

import com.fagundes.myshowlist.core.data.mapper.toDomain
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.domain.Movie

class CatalogRepositoryImpl(
    private val movieApi: MovieApi,
) : CatalogRepository {

    override suspend fun getMoviesByCategory(category: Int): Result<List<Movie>> = runCatching {
        movieApi.getMoviesByCategory(category)
            .results
            .map { it.toDomain() }
    }

    override suspend fun searchMoviesByName(query: String): Result<List<Movie>> = runCatching {
        movieApi.getMoviesByName(query)
            .results
            .map { it.toDomain() }
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>> = runCatching {
        movieApi.getUpcomingMovies()
            .results
            .map { it.toDomain() }
    }
}