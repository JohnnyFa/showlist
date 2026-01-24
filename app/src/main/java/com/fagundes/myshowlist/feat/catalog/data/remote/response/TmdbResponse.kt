package com.fagundes.myshowlist.feat.catalog.data.remote.response

import com.fagundes.myshowlist.feat.catalog.data.remote.dto.MovieDto
import kotlinx.serialization.Serializable

@Serializable
data class TmdbResponse(
    val page: Int,
    val results: List<MovieDto>
)