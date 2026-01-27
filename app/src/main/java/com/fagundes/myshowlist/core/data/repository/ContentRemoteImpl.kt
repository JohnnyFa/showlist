package com.fagundes.myshowlist.core.data.repository

import android.util.Log
import com.fagundes.myshowlist.core.CACHE_DURATION
import com.fagundes.myshowlist.core.data.local.datasource.ContentLocalDataSource
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.core.data.local.mapper.toDetailUi
import com.fagundes.myshowlist.core.data.local.mapper.toEntity
import com.fagundes.myshowlist.core.data.remote.datasource.ContentRemoteDataSource
import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi

class ContentRepositoryImpl(
    private val remote: ContentRemoteDataSource,
    private val local: ContentLocalDataSource
) : ContentRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> =
        runCatching {
            val minValidTime = System.currentTimeMillis() - CACHE_DURATION

            val cached = local.getMoviesByCategory(
                category = ContentCategory.POPULAR,
                maxAgeMillis = minValidTime
            )

            if (cached.isNotEmpty()) {
                Log.d("CACHE", "Returning popular movies from CACHE")
                return@runCatching cached
            }
            Log.d("CACHE", "Returning popular movies from API")

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
            val minValidTime = System.currentTimeMillis() - CACHE_DURATION

            val cached = local.getMoviesByCategory(
                ContentCategory.RECOMMENDED,
                minValidTime
            )

            if (cached.isNotEmpty()) {
                Log.d("CACHE", "Returning recommended movies from CACHE")
                return@runCatching cached
            }

            Log.d("CACHE", "Returning recommended movies from API")
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

    override suspend fun getContentDetail(
        id: String,
        type: String
    ): Result<ContentDetailUi> = runCatching {

        val entity = local.getContentById(id.toInt())
        Log.d("CACHE", "Detail from CACHE")
        return@runCatching entity.toDetailUi()
    }


    override suspend fun getAnimes(): Result<List<Anime>> =
        runCatching {
            remote.getTopAnimes()
        }
}
