package com.fagundes.myshowlist.feat.home.data.local.datasource

import com.fagundes.myshowlist.core.data.local.dao.ContentDao
import com.fagundes.myshowlist.core.data.local.entity.ContentEntity
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.mapper.toMovie
import com.fagundes.myshowlist.core.domain.Movie

class HomeLocalDataSourceImpl(
    private val dao: ContentDao
) : HomeLocalDataSource {

    override suspend fun getContentById(id: Int): ContentEntity {
        return dao.getById(id) ?: throw IllegalArgumentException("Content not found")
    }

    override suspend fun saveMovies(items: List<ContentEntity>) {
        try {
            dao.insertAll(items)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getMoviesByCategory(
        category: ContentCategory,
        maxAgeMillis: Long
    ): List<Movie> {
        return dao.getMoviesByCategory(category, maxAgeMillis).map { it.toMovie() }
    }
}