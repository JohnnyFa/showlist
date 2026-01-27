package com.fagundes.myshowlist.core.data.local.datasource

import com.fagundes.myshowlist.core.data.local.dao.ContentDao
import com.fagundes.myshowlist.core.data.local.entity.ContentEntity
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.core.data.local.mapper.toAnime
import com.fagundes.myshowlist.core.data.local.mapper.toMovie
import com.fagundes.myshowlist.core.data.local.mapper.toTvShow
import com.fagundes.myshowlist.core.domain.Content
import com.fagundes.myshowlist.core.domain.Movie

class ContentLocalDataSourceImpl(
    private val dao: ContentDao
) : ContentLocalDataSource {

    override suspend fun getContentById(id: Int): Content? {
        val entity = dao.getById(id) ?: return null

        return when (entity.type) {
            ContentType.MOVIE -> entity.toMovie()
            ContentType.TV -> entity.toTvShow()
            ContentType.ANIME -> entity.toAnime()
        }
    }

    override suspend fun saveMovies(items: List<ContentEntity>) {
        dao.insertAll(items)
    }


    override suspend fun saveAllAnimes(items: List<ContentEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesByCategory(
        category: ContentCategory,
        maxAgeMillis: Long
    ): List<Movie> {
        return dao.getMoviesByCategory(category, maxAgeMillis).map { it.toMovie() }
    }
}