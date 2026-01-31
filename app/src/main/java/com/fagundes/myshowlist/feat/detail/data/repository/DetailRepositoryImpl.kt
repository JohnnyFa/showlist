package com.fagundes.myshowlist.feat.detail.data.repository

import com.fagundes.myshowlist.core.data.mapper.toContentDetailUi
import com.fagundes.myshowlist.core.data.mapper.toDomain
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi

class DetailRepositoryImpl(
    private val movieApi: MovieApi
) : DetailRepository {
    override suspend fun getContentDetail(
        id: String,
        type: String
    ): Result<ContentDetailUi> = runCatching {

        val dto = movieApi.getContentById(id.toInt())
        return@runCatching dto
            .toDomain()
            .toContentDetailUi(type)
    }
}