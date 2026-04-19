package com.fagundes.myshowlist.feat.home.vm

import android.util.Log
import com.fagundes.myshowlist.core.domain.Movie
import com.fagundes.myshowlist.feat.home.data.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val auth: FirebaseAuth = mockk(relaxed = true)
    private val repository: HomeRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel

    private val movie = Movie(1, "Test Movie", "url", "Overview", 8.0)
    private val movieList = listOf(movie)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        
        // Set up default successful responses for init block
        coEvery { repository.getPopularMovies() } returns Result.success(movieList)
        coEvery { repository.getRecommended() } returns Result.success(movieList)
        coEvery { repository.getShowOfTheDay() } returns Result.success(movie)
        
        viewModel = HomeViewModel(auth, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial state should load data correctly`() = runTest {
        testDispatcher.scheduler.runCurrent()
        
        assert(viewModel.trendingState.value is HomeUiState.Success)
        assertEquals(movieList, (viewModel.trendingState.value as HomeUiState.Success).data)
        
        assert(viewModel.forYouState.value is HomeUiState.Success)
        assertEquals(movieList, (viewModel.forYouState.value as HomeUiState.Success).data)
        
        assert(viewModel.showOfTheDay.value is HomeUiState.Success)
        assertEquals(movie, (viewModel.showOfTheDay.value as HomeUiState.Success).data)
    }

    @Test
    fun `loadPopular should update trendingState to Success on success`() = runTest {
        coEvery { repository.getPopularMovies() } returns Result.success(movieList)
        
        viewModel.loadPopular()
        // We might not catch Loading state because it happens too fast in this dispatcher setup
        // or it's already in Success from init.
        
        testDispatcher.scheduler.runCurrent()
        
        assert(viewModel.trendingState.value is HomeUiState.Success)
        assertEquals(movieList, (viewModel.trendingState.value as HomeUiState.Success).data)
    }

    @Test
    fun `loadPopular should update trendingState to Error on failure`() = runTest {
        coEvery { repository.getPopularMovies() } returns Result.failure(Exception("Network error"))
        
        viewModel.loadPopular()
        testDispatcher.scheduler.runCurrent()
        
        assert(viewModel.trendingState.value is HomeUiState.Error)
        assertEquals("Failed to load trending", (viewModel.trendingState.value as HomeUiState.Error).message)
    }

    @Test
    fun `loadRecommended should update forYouState to Success on success`() = runTest {
        coEvery { repository.getRecommended() } returns Result.success(movieList)
        
        viewModel.loadRecommended()
        testDispatcher.scheduler.runCurrent()
        
        assert(viewModel.forYouState.value is HomeUiState.Success)
        assertEquals(movieList, (viewModel.forYouState.value as HomeUiState.Success).data)
    }

    @Test
    fun `loadShowOfTheDay should update showOfTheDay state to Success on success`() = runTest {
        coEvery { repository.getShowOfTheDay() } returns Result.success(movie)
        
        viewModel.loadShowOfTheDay()
        testDispatcher.scheduler.runCurrent()
        
        assert(viewModel.showOfTheDay.value is HomeUiState.Success)
        assertEquals(movie, (viewModel.showOfTheDay.value as HomeUiState.Success).data)
    }

    @Test
    fun `logout should call auth signOut`() {
        viewModel.logout()
        verify { auth.signOut() }
    }
}
