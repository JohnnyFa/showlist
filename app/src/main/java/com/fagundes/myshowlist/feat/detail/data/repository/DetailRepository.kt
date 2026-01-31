package com.fagundes.myshowlist.feat.detail.data.repository

import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi

interface DetailRepository {
    suspend fun getContentDetail(id: String, type: String): Result<ContentDetailUi>
}