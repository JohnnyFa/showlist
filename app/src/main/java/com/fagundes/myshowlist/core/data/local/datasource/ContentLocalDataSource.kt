package com.fagundes.myshowlist.core.data.local.datasource

import com.fagundes.myshowlist.core.data.local.entity.ContentEntity
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.domain.Content
import com.fagundes.myshowlist.core.domain.Movie

interface ContentLocalDataSource {
    suspend fun getContentById(id: Int): Content?
    suspend fun saveMovies(items: List<ContentEntity>)
    suspend fun saveAllAnimes(items: List<ContentEntity>)


    // --- GET --- //
    suspend fun getMoviesByCategory(
        category: ContentCategory,
        maxAgeMillis: Long
    ): List<Movie>
}