package com.fagundes.myshowlist.feat.home.data.local.datasource

import com.fagundes.myshowlist.core.data.local.entity.ContentEntity
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.domain.Movie

interface HomeLocalDataSource {
    suspend fun getContentById(id: Int): ContentEntity
    suspend fun saveMovies(items: List<ContentEntity>)
    suspend fun getMoviesByCategory(
        category: ContentCategory,
        maxAgeMillis: Long
    ): List<Movie>
}