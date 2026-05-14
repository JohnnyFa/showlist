package com.fagundes.myshowlist.feat.detail.data.repository

import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi
import com.fagundes.myshowlist.feat.detail.domain.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getContentDetail(id: String, type: String): Result<ContentDetailUi>
    suspend fun cacheFavoriteCandidate(item: FavoriteItem)
    fun observeFavoriteState(id: Int): Flow<Boolean>
    suspend fun toggleFavorite(id: Int): Result<Boolean>
}
