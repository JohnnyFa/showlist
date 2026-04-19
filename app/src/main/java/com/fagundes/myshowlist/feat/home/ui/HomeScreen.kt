package com.fagundes.myshowlist.feat.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fagundes.myshowlist.R
import com.fagundes.myshowlist.components.error.ErrorSection
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.feat.home.ui.components.RecommendedForYouSection
import com.fagundes.myshowlist.feat.home.ui.components.ShowOfTheDaySection
import com.fagundes.myshowlist.feat.home.ui.components.TrendingNowSection
import com.fagundes.myshowlist.feat.home.ui.components.skeleton.CarouselSkeleton
import com.fagundes.myshowlist.feat.home.ui.components.skeleton.ShowOfTheDaySkeleton
import com.fagundes.myshowlist.feat.home.vm.HomeUiState
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.ui.theme.MyShowListTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLogout: () -> Unit,
    onOpenDetail: (Int, ContentType) -> Unit
) {
    val trendingState by viewModel.trendingState.collectAsState()
    val forYouState by viewModel.forYouState.collectAsState()
    val showOfTheDayState by viewModel.showOfTheDay.collectAsState()

    HomeScreenContent(
        trendingState = trendingState,
        forYouState = forYouState,
        showOfTheDayState = showOfTheDayState,
        onOpenDetail = onOpenDetail,
        onLogout = onLogout,
        onRetry = HomeRetryActions(
            onRetryShowOfTheDay = { viewModel.loadShowOfTheDay() },
            onRetryPopular = { viewModel.loadPopular() },
            onRetryRecommended = { viewModel.loadRecommended() }
        )
    )
}

@Composable
fun HomeScreenContent(
    trendingState: HomeUiState<List<Movie>>,
    forYouState: HomeUiState<List<Movie>>,
    showOfTheDayState: HomeUiState<Movie>,
    onOpenDetail: (Int, ContentType) -> Unit,
    onLogout: () -> Unit,
    onRetry: HomeRetryActions
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_content")
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = WindowInsets.statusBars.asPaddingValues()
        ) {

            item {
                Box(modifier = Modifier.testTag("show_of_the_day_container")) {
                    when (showOfTheDayState) {
                        HomeUiState.Loading -> ShowOfTheDaySkeleton()
                        is HomeUiState.Success ->
                            ShowOfTheDaySection(
                                movie = showOfTheDayState.data,
                                onMovieClick = { movie ->
                                    onOpenDetail(movie.id, ContentType.MOVIE)
                                }
                            )

                        is HomeUiState.Error ->
                            ErrorSection(
                                onRetry = onRetry.onRetryShowOfTheDay
                            )

                        else -> Unit
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Box(modifier = Modifier.testTag("trending_now_container")) {
                    when (trendingState) {
                        HomeUiState.Loading -> CarouselSkeleton()

                        is HomeUiState.Success ->
                            TrendingNowSection(
                                movies = trendingState.data,
                                onMovieClick = { movie ->
                                    onOpenDetail(movie.id, ContentType.MOVIE)
                                }
                            )


                        is HomeUiState.Error ->
                            ErrorSection(
                                onRetry = onRetry.onRetryPopular
                            )

                        else -> Unit
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Box(modifier = Modifier.testTag("for_you_container")) {
                    when (forYouState) {
                        HomeUiState.Loading -> CarouselSkeleton()

                        is HomeUiState.Success ->
                            RecommendedForYouSection(
                                movies = forYouState.data,
                                onMovieClick = { movie ->
                                    onOpenDetail(movie.id, ContentType.MOVIE)
                                }
                            )

                        is HomeUiState.Error ->
                            ErrorSection(
                                onRetry = onRetry.onRetryRecommended
                            )

                        else -> Unit
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onLogout,
                    modifier = Modifier.testTag("logout_button")
                ) {
                    Text(stringResource(R.string.leave))
                }
            }
        }
    }
}

data class HomeRetryActions(
    val onRetryShowOfTheDay: () -> Unit,
    val onRetryPopular: () -> Unit,
    val onRetryRecommended: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyShowListTheme {
        HomeScreenContent(
            trendingState = HomeUiState.Success(
                listOf(
                    Movie(1, "Trending 1", null, null, 8.0),
                    Movie(2, "Trending 2", null, null, 7.5)
                )
            ),
            forYouState = HomeUiState.Success(
                listOf(
                    Movie(3, "Recommended 1", null, null, 9.0),
                    Movie(4, "Recommended 2", null, null, 8.5)
                )
            ),
            showOfTheDayState = HomeUiState.Success(
                Movie(5, "Show of the Day", null, "Overview", 9.5)
            ),
            onOpenDetail = { _, _ -> },
            onLogout = {},
            onRetry = HomeRetryActions({}, {}, {})
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
    MyShowListTheme {
        HomeScreenContent(
            trendingState = HomeUiState.Loading,
            forYouState = HomeUiState.Loading,
            showOfTheDayState = HomeUiState.Loading,
            onOpenDetail = { _, _ -> },
            onLogout = {},
            onRetry = HomeRetryActions({}, {}, {})
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
    MyShowListTheme {
        HomeScreenContent(
            trendingState = HomeUiState.Error("Error"),
            forYouState = HomeUiState.Error("Error"),
            showOfTheDayState = HomeUiState.Error("Error"),
            onOpenDetail = { _, _ -> },
            onLogout = {},
            onRetry = HomeRetryActions({}, {}, {})
        )
    }
}