package com.fagundes.myshowlist.feat.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fagundes.myshowlist.components.error.ErrorSection
import com.fagundes.myshowlist.components.error.RetryButton
import com.fagundes.myshowlist.feat.home.ui.components.RecommendedForYouSection
import com.fagundes.myshowlist.feat.home.ui.components.ShowOfTheDaySection
import com.fagundes.myshowlist.feat.home.ui.components.TrendingNowSection
import com.fagundes.myshowlist.feat.home.ui.components.skeleton.CarouselSkeleton
import com.fagundes.myshowlist.feat.home.ui.components.skeleton.ShowOfTheDaySkeleton
import com.fagundes.myshowlist.feat.home.vm.HomeUiState
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    onLogout: () -> Unit
) {
    val trendingState by viewModel.trendingState.collectAsState()
    val forYouState by viewModel.forYouState.collectAsState()
    val showOfTheDayState by viewModel.showOfTheDay.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            item {
                when (val state = showOfTheDayState) {
                    HomeUiState.Loading -> ShowOfTheDaySkeleton()
                    is HomeUiState.Success ->
                        ShowOfTheDaySection(
                            movie = state.data,
                            onMovieClick = { movie ->
                                navController.navigate("detail/movie/${movie.id}")
                            }
                        )

                    is HomeUiState.Error ->
                        ErrorSection(
                            message = "Failed to load trending movies"
                        ) {
                            RetryButton(
                                onClick = { viewModel.loadShowOfTheDay() }
                            )
                        }

                    else -> Unit
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                when (val state = trendingState) {
                    HomeUiState.Loading -> CarouselSkeleton()

                    is HomeUiState.Success ->
                        TrendingNowSection(
                            movies = state.data,
                            onMovieClick = { movie ->
                                navController.navigate("detail/movie/${movie.id}")
                            }
                        )


                    is HomeUiState.Error ->
                        ErrorSection(
                            message = "Failed to load trending movies"
                        ) {
                            RetryButton(
                                onClick = { viewModel.loadPopular() }
                            )
                        }

                    else -> Unit
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                when (val state = forYouState) {
                    HomeUiState.Loading -> CarouselSkeleton()

                    is HomeUiState.Success ->
                        RecommendedForYouSection(
                            movies = state.data,
                            onMovieClick = { movie ->
                                navController.navigate("detail/movie/${movie.id}")
                            }
                        )

                    is HomeUiState.Error ->
                        RetryButton(
                            onClick = { viewModel.loadRecommended() }
                        )


                    else -> Unit
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onLogout) {
                    Text("Sair")
                }
            }
        }
    }
}