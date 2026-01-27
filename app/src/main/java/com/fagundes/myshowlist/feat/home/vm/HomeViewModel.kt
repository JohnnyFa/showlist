package com.fagundes.myshowlist.feat.home.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fagundes.myshowlist.core.data.repository.ContentRepository
import com.fagundes.myshowlist.core.domain.Movie
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val auth: FirebaseAuth,
    private val repository: ContentRepository
) : ViewModel() {

    private val _trendingState = MutableStateFlow<HomeUiState<List<Movie>>>(HomeUiState.Idle)
    val trendingState: StateFlow<HomeUiState<List<Movie>>> = _trendingState

    private val _forYouState = MutableStateFlow<HomeUiState<List<Movie>>>(HomeUiState.Idle)
    val forYouState: StateFlow<HomeUiState<List<Movie>>> = _forYouState

    private val _showOfTheDayState = MutableStateFlow<HomeUiState<Movie>>(HomeUiState.Idle)
    val showOfTheDay: StateFlow<HomeUiState<Movie>> = _showOfTheDayState

    init {
        loadPopular()
        loadRecommended()
        loadShowOfTheDay()
    }

    fun loadPopular() {
        Log.d("HomeViewModel", "loadPopular called")
        viewModelScope.launch {
            _trendingState.value = HomeUiState.Loading

            repository.getPopular()
                .onSuccess {
                    _trendingState.value = HomeUiState.Success(it)
                }
                .onFailure {
                    _trendingState.value =
                        HomeUiState.Error("Failed to load trending")
                }
        }
    }

    fun loadRecommended() {
        Log.d("HomeViewModel", "loadRecommended called")
        viewModelScope.launch {
            _forYouState.value = HomeUiState.Loading

            repository.getRecommended()
                .onSuccess {
                    _forYouState.value = HomeUiState.Success(it)
                }
                .onFailure {
                    _forYouState.value =
                        HomeUiState.Error("Failed to load recommended")
                }

        }
    }

    fun loadShowOfTheDay() {
        Log.d("HomeViewModel", "loadShowOfTheDay called")
        viewModelScope.launch {
            _showOfTheDayState.value = HomeUiState.Loading

            repository.getShowOfTheDay()
                .onSuccess {
                    _showOfTheDayState.value = HomeUiState.Success(it)
                }
                .onFailure {
                    _showOfTheDayState.value =
                        HomeUiState.Error("Failed to load show of the day")
                }
        }
    }

    fun logout() {
        auth.signOut()
    }
}

sealed interface HomeUiState<out T> {
    object Idle : HomeUiState<Nothing>
    object Loading : HomeUiState<Nothing>
    data class Success<T>(val data: T) : HomeUiState<T>
    data class Error(val message: String) : HomeUiState<Nothing>
}