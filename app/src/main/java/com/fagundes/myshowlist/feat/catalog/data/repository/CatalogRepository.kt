package com.fagundes.myshowlist.feat.catalog.data.repository

import com.fagundes.myshowlist.feat.catalog.domain.Anime
import com.fagundes.myshowlist.feat.catalog.domain.Movie

interface CatalogRepository {
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getAnimes(): Result<List<Anime>>
}
