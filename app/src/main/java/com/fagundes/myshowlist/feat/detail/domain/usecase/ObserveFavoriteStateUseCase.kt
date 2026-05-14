package com.fagundes.myshowlist.feat.detail.domain.usecase

import com.fagundes.myshowlist.feat.detail.data.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteStateUseCase(
    private val repository: DetailRepository
) {
    operator fun invoke(itemId: Int): Flow<Boolean> = repository.observeFavoriteState(itemId)
}
