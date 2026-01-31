package com.fagundes.myshowlist.feat.catalog.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fagundes.myshowlist.core.data.repository.ContentRepository
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.feat.catalog.domain.MovieGenre
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val repository: ContentRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState

    private var baseMovies: List<Movie> = emptyList()

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearch()
    }

    fun loadIfNeeded() {
        if (_uiState.value !is CatalogUiState.Content) {
            loadCatalog()
        }
    }

    fun onSeeAllUpcoming() {}

    fun retry() {
        _uiState.value = CatalogUiState.Loading
        loadCatalog()
    }

    fun onCategorySelected(category: MovieGenre) {
        val current =
            (_uiState.value as? CatalogUiState.Content)?.ui
                ?: CatalogContentState()

        if (category == MovieGenre.ALL) {
            loadCatalog()
            return
        }

        viewModelScope.launch {
            repository.getMoviesByCategory(category.genreId!!)
                .onSuccess { movies ->
                    baseMovies = movies
                    _uiState.value = CatalogUiState.Content(
                        current.copy(
                            selectedCategory = category,
                            movies = movies,
                            featuredMovie = movies.randomOrNull()
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
            _uiState.value = CatalogUiState.Content(
                current.copy(
                    searchQuery = "",
                    movies = baseMovies
                )
            )
            return
        }

        _uiState.value = CatalogUiState.Content(
            current.copy(searchQuery = query)
        )

        searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(600)
                .filter { it.length >= 2 }
                .collectLatest { query ->
                    repository.searchMoviesByName(query)
                        .onSuccess { movies ->
                            val current =
                                (_uiState.value as? CatalogUiState.Content)?.ui
                                    ?: CatalogContentState()

                            _uiState.value = CatalogUiState.Content(
                                current.copy(
                                    movies = movies
                                )
                            )
                        }
                }
        }
    }

    private fun loadCatalog() = viewModelScope.launch {
        repository.getUpcomingMovies()
            .onSuccess { movies ->
                baseMovies = movies
                _uiState.value = CatalogUiState.Content(
                    CatalogContentState(
                        movies = movies,
                        featuredMovie = movies.randomOrNull()
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
}


data class CatalogContentState(
    val searchQuery: String = "",
    val selectedCategory: MovieGenre = MovieGenre.ALL,
    val movies: List<Movie> = emptyList(),
    val featuredMovie: Movie? = null
)