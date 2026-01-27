package com.fagundes.myshowlist.core.data.repository

import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie

interface ContentRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getRecommended(): Result<List<Movie>>
    suspend fun getAnimes(): Result<List<Anime>>
    suspend fun getShowOfTheDay(): Result<Movie>
}