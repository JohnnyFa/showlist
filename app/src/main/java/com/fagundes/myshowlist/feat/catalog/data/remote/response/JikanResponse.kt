package com.fagundes.myshowlist.feat.catalog.data.remote.response

import com.fagundes.myshowlist.feat.catalog.data.remote.dto.AnimeDto
import kotlinx.serialization.Serializable

@Serializable
data class JikanResponse(
    val data: List<AnimeDto>
)