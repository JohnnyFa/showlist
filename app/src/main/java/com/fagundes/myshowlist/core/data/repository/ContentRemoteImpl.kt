package com.fagundes.myshowlist.core.data.repository

import com.fagundes.myshowlist.core.CACHE_DURATION
import com.fagundes.myshowlist.core.data.local.datasource.ContentLocalDataSource
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.core.data.local.mapper.toEntity
import com.fagundes.myshowlist.core.data.remote.datasource.ContentRemoteDataSource
import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie

class ContentRepositoryImpl(
    private val remote: ContentRemoteDataSource,
    private val local: ContentLocalDataSource
) : ContentRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> =
        runCatching {

            val cached = local.getMoviesByCategory(
                category = ContentCategory.POPULAR,
                maxAgeMillis = CACHE_DURATION
            )

            if (cached.isNotEmpty()) {
                return@runCatching cached
            }

            val remoteMovies = remote.getPopularMovies()

            local.saveMovies(
                remoteMovies.map {
                    it.toEntity(
                        contentType = ContentType.MOVIE,
                        category = ContentCategory.POPULAR
                    )
                }
            )

            remoteMovies
        }

    override suspend fun getRecommended(): Result<List<Movie>> =
        runCatching {

            val cached = local.getMoviesByCategory(
                ContentCategory.RECOMMENDED,
                CACHE_DURATION
            )

            if (cached.isNotEmpty()) {
                return@runCatching cached
            }

            val remoteMovies = remote.getRecommendedMovies()

            local.saveMovies(
                remoteMovies.map {
                    it.toEntity(
                        ContentType.MOVIE,
                        ContentCategory.RECOMMENDED
                    )
                }
            )

            remoteMovies
        }


    override suspend fun getShowOfTheDay(): Result<Movie> =
        runCatching {
            remote.getShowOfTheDay()
        }

    override suspend fun getAnimes(): Result<List<Anime>> =
        runCatching {
            remote.getTopAnimes()
        }
}
