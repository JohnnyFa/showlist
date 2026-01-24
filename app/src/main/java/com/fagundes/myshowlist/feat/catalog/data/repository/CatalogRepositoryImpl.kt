package com.fagundes.myshowlist.feat.catalog.data.repository

import com.fagundes.myshowlist.feat.catalog.data.remote.api.AnimeApi
import com.fagundes.myshowlist.feat.catalog.data.remote.api.MovieApi
import com.fagundes.myshowlist.feat.catalog.domain.Anime
import com.fagundes.myshowlist.feat.catalog.domain.Movie
import com.fagundes.myshowlist.feat.catalog.data.mapper.toDomain


class CatalogRepositoryImpl(
    private val movieApi: MovieApi,
    private val animeApi: AnimeApi
) : CatalogRepository {

    override suspend fun getMovies(): Result<List<Movie>> =
        runCatching {
            movieApi.getPopularMovies()
                .results
                .map { it.toDomain() }
        }

    override suspend fun getAnimes(): Result<List<Anime>> =
        runCatching {
            animeApi.getTopAnimes()
                .data
                .map { it.toDomain() }
        }

}
