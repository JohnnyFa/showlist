package com.fagundes.myshowlist.feat.home.data.repository

import com.fagundes.myshowlist.core.domain.Movie

interface HomeRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getRecommended(): Result<List<Movie>>
    suspend fun getShowOfTheDay(): Result<Movie>
}