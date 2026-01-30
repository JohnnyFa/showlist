package com.fagundes.myshowlist.core.data.repository

import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi

interface ContentRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getRecommended(): Result<List<Movie>>
    suspend fun getAnimes(): Result<List<Anime>>
    suspend fun getShowOfTheDay(): Result<Movie>
    suspend fun getContentDetail(id: String, type: String): Result<ContentDetailUi>
    suspend fun getMoviesByCategory(category: Int): Result<List<Movie>>
    suspend fun searchMoviesByName(query: String): Result<List<Movie>>
}