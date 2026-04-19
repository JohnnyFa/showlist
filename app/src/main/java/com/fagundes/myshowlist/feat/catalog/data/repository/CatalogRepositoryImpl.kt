package com.fagundes.myshowlist.feat.catalog.data.repository

import android.util.Log
import com.fagundes.myshowlist.core.CACHE_DURATION
import com.fagundes.myshowlist.core.data.mapper.toDomain
import com.fagundes.myshowlist.core.data.local.mapper.toEntity
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.core.CACHE_DURATION
import com.fagundes.myshowlist.feat.home.data.local.datasource.HomeLocalDataSource

class CatalogRepositoryImpl(
    private val movieApi: MovieApi,
    private val local: HomeLocalDataSource
) : CatalogRepository {

    override suspend fun getMoviesByCategory(category: Int): Result<List<Movie>> = runCatching {
        val minValidTime = System.currentTimeMillis() - CACHE_DURATION

        val contentCategory = when (category) {
            28 -> ContentCategory.TRENDING // Action
            12 -> ContentCategory.TRENDING // Adventure (reusing TRENDING for now or we could add more)
            16 -> ContentCategory.TRENDING // Animation
            35 -> ContentCategory.TRENDING // Comedy
            99 -> ContentCategory.TRENDING // Documentary
            18 -> ContentCategory.TRENDING // Drama
            27 -> ContentCategory.TRENDING // Horror
            10749 -> ContentCategory.TRENDING // Romance
            878 -> ContentCategory.TRENDING // Science Fiction
            else -> null
        }

        if (contentCategory != null) {
            val cached = local.getMoviesByCategory(contentCategory, minValidTime)
            if (cached.isNotEmpty()) {
                Log.d("CACHE", "Returning movies by category $category from CACHE")
                return@runCatching cached
            }
        }

        Log.d("CatalogRepository", "Fetching movies by category: $category from API")
        val remoteMovies = movieApi.getMoviesByCategory(category)
            .results
            .map { it.toDomain() }

        if (contentCategory != null) {
            local.saveMovies(
                remoteMovies.map {
                    it.toEntity(
                        contentType = ContentType.MOVIE,
                        category = contentCategory
                    )
                }
            )
        }

        remoteMovies
    }

    override suspend fun searchMoviesByName(query: String): Result<List<Movie>> = runCatching {
        movieApi.getMoviesByName(query)
            .results
            .map { it.toDomain() }
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>> = runCatching {
        val minValidTime = System.currentTimeMillis() - CACHE_DURATION

        val cached = local.getMoviesByCategory(
            category = ContentCategory.UPCOMING,
            maxAgeMillis = minValidTime
        )

        if (cached.isNotEmpty()) {
            return@runCatching cached
        }

        val remoteMovies = movieApi.getUpcomingMovies()
            .results
            .map { it.toDomain() }

        local.saveMovies(
            remoteMovies.map {
                it.toEntity(
                    contentType = ContentType.MOVIE,
                    category = ContentCategory.UPCOMING
                )
            }
        )

        remoteMovies
    }
}