package com.fagundes.myshowlist.feat.catalog.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fagundes.myshowlist.core.data.repository.ContentRepository
import com.fagundes.myshowlist.core.domain.Anime
import com.fagundes.myshowlist.core.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val repository: ContentRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState

    init {
        loadCatalog()
    }

    private fun loadCatalog() = viewModelScope.launch {
        viewModelScope.launch {
            val moviesResult = repository.getPopularMovies()
            val animesResult = repository.getAnimes()

            if (moviesResult.isSuccess && animesResult.isSuccess) {
                _uiState.value = CatalogUiState.Success(
                    movies = moviesResult.getOrThrow(),
                    animes = animesResult.getOrThrow()
                )
            } else {
                _uiState.value =
                    CatalogUiState.Error("Erro ao carregar catálogo")
            }
        }
    }
}

sealed interface CatalogUiState {

    object Loading : CatalogUiState

    data class Success(
        val movies: List<Movie>,
        val animes: List<Anime>
    ) : CatalogUiState

    data class Error(
        val message: String
    ) : CatalogUiState
}