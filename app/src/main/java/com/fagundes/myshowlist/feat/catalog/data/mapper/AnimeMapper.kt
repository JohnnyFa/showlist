package com.fagundes.myshowlist.feat.catalog.data.mapper

import com.fagundes.myshowlist.feat.catalog.data.remote.dto.MovieDto
import com.fagundes.myshowlist.feat.catalog.domain.Movie

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let {
            "https://image.tmdb.org/t/p/w500$it"
        }
    )
