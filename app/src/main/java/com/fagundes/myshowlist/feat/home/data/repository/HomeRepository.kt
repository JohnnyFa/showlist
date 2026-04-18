package com.fagundes.myshowlist.feat.home.data.repository

import com.fagundes.myshowlist.core.domain.Movie
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observePopularMovies(): Flow<Result<List<Movie>>>
    suspend fun getRecommended(): Result<List<Movie>>
    suspend fun getShowOfTheDay(): Result<Movie>
}
