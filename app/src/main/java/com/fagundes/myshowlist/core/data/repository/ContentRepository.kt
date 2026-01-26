package com.fagundes.myshowlist.core.data.repository

import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie

interface ContentRepository {
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getAnimes(): Result<List<Anime>>
}
