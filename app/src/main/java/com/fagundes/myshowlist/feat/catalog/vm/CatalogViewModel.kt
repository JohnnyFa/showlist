package com.fagundes.myshowlist.feat.catalog.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fagundes.myshowlist.core.data.repository.ContentRepository
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

    fun onSeeAllUpcoming() {
        //TODO: this function shouldn`t even exist
    }

    fun retry() {
        _uiState.value = CatalogUiState.Loading
        loadCatalog()
    }

    fun onCategorySelected(category: String) {
        val current =
            (_uiState.value as? CatalogUiState.Content)?.ui
                ?: CatalogContentState()

        if (category == "All") {
            loadCatalog()
            return
        }

        viewModelScope.launch {
            repository.getMoviesByCategory(category)
                .onSuccess { movies ->
                    _uiState.value = CatalogUiState.Content(
                        current.copy(
                            selectedCategory = category,
                            movies = movies
                        )
                    )
                }
                .onFailure {
                    _uiState.value =
                        CatalogUiState.Error("Erro ao carregar categoria")
                }
        }
    }

    fun onSearchChange(query: String) {
        val current =
            (_uiState.value as? CatalogUiState.Content)?.ui
                ?: CatalogContentState()

        if (query.isBlank()) {
            loadCatalog()
            return
        }

        viewModelScope.launch {
            repository.searchMoviesByName(query)
                .onSuccess { movies ->
                    _uiState.value = CatalogUiState.Content(
                        current.copy(
                            searchQuery = query,
                            movies = movies
                        )
                    )
                }
                .onFailure {
                    _uiState.value = CatalogUiState.Error("Failed to search movies")
                }
        }
    }

    private fun loadCatalog() = viewModelScope.launch {
        repository.getPopularMovies()
            .onSuccess { movies ->
                _uiState.value = CatalogUiState.Content(
                    CatalogContentState(
                        movies = movies
                    )
                )
            }
            .onFailure {
                _uiState.value = CatalogUiState.Error("Erro ao carregar catálogo")
            }
    }
}

sealed interface CatalogUiState {

    object Loading : CatalogUiState

    data class Content(
        val ui: CatalogContentState
    ) : CatalogUiState

    data class Error(
        val message: String
    ) : CatalogUiState

    object Empty : CatalogUiState
}


data class CatalogContentState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val movies: List<Movie> = emptyList()
)
