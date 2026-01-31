package com.fagundes.myshowlist.feat.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.feat.detail.data.repository.DetailRepository
import com.fagundes.myshowlist.feat.detail.domain.ContentDetailUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val type: ContentType,
    private val repository: DetailRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            repository.getContentDetail(id.toString(), type.name)
                .onSuccess {
                    _uiState.value =
                        DetailUiState.Success(it)
                }
                .onFailure {
                    _uiState.value =
                        DetailUiState.Error("Failed to load content")
                }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val ui: ContentDetailUi) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}