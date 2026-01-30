package com.fagundes.myshowlist.feat.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.feat.catalog.domain.MovieGenre
import com.fagundes.myshowlist.feat.catalog.ui.components.CatalogEmpty
import com.fagundes.myshowlist.feat.catalog.ui.components.CatalogError
import com.fagundes.myshowlist.feat.catalog.ui.components.CatalogLoading
import com.fagundes.myshowlist.feat.catalog.ui.components.CatalogSearchBar
import com.fagundes.myshowlist.feat.catalog.ui.components.CategoryChipsRow
import com.fagundes.myshowlist.feat.catalog.ui.components.UpcomingHighlightCard
import com.fagundes.myshowlist.feat.catalog.ui.components.UpcomingMovieItem
import com.fagundes.myshowlist.feat.catalog.vm.CatalogUiState
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
import com.fagundes.myshowlist.ui.theme.Background

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(Modifier.height(16.dp))

        when (state) {
            CatalogUiState.Loading -> {
                CatalogLoading()
            }

            is CatalogUiState.Error -> {
                CatalogError(
                    onRetry = viewModel::retry
                )
            }

            CatalogUiState.Empty -> {
                CatalogEmpty()
            }

            is CatalogUiState.Content -> {
                val ui = (state as CatalogUiState.Content).ui

                CatalogHeader(
                    searchQuery = ui.searchQuery,
                    selectedCategory = ui.selectedCategory,
                    onSearchChange = viewModel::onSearchChange,
                    onCategorySelected = viewModel::onCategorySelected
                )

                Spacer(Modifier.height(24.dp))

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    item {
                        CatalogContent(
                            movies = ui.movies,
                            onSeeAllUpcoming = viewModel::onSeeAllUpcoming
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CatalogHeader(
    searchQuery: String,
    selectedCategory: MovieGenre,
    onSearchChange: (String) -> Unit,
    onCategorySelected: (MovieGenre) -> Unit
) {
    CatalogSearchBar(
        value = searchQuery,
        onSearchChange = onSearchChange
    )

    Spacer(Modifier.height(16.dp))

    CategoryChipsRow(
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected
    )
}

@Composable
fun CatalogContent(
    movies: List<Movie>,
    onSeeAllUpcoming: () -> Unit
) {
    val featuredMovie = remember(movies) {
        movies.randomOrNull()
    }

    UpcomingHighlightCard(
        movie = featuredMovie,
        onSeeAll = onSeeAllUpcoming
    )

    Spacer(Modifier.height(24.dp))

    movies
        .filterNot { it.id == featuredMovie?.id }
        .forEach { movie ->
            UpcomingMovieItem(movie)
        }
}