package com.fagundes.myshowlist.feat.catalog.data.mapper

import com.fagundes.myshowlist.feat.catalog.data.remote.dto.AnimeDto
import com.fagundes.myshowlist.feat.catalog.domain.Anime

fun AnimeDto.toDomain(): Anime =
    Anime(
        id = malId,
        title = title,
        imageUrl = images.jpg.imageUrl
    )
