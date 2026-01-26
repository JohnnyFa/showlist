package com.fagundes.myshowlist.core.data.repository

import com.fagundes.myshowlist.core.data.remote.api.AnimeApi
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.core.data.mapper.toDomain


class ContentRepositoryImpl(
    private val movieApi: MovieApi,
    private val animeApi: AnimeApi
) : ContentRepository {

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
