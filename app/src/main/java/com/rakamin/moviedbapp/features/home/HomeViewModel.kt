package com.rakamin.moviedbapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import com.rakamin.moviedbapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _nowPlayingMovies = MutableStateFlow<ResponseResult<List<MovieResponse>>>(ResponseResult.Loading)
    val nowPlayingMovies = _nowPlayingMovies.asLiveData()
    fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getNowPlayingMovies()
                .catch { 
                    _nowPlayingMovies.emit(ResponseResult.Error(it)) 
                }
                .collectLatest { result ->
                    when(result) {
                        is ResponseResult.Loading -> {
                            _nowPlayingMovies.emit(ResponseResult.Loading)
                        }
                        is ResponseResult.Error -> {
                            _nowPlayingMovies.emit(ResponseResult.Error(result.throwable))
                        }
                        is ResponseResult.Success -> {
                            _nowPlayingMovies.emit(ResponseResult.Success(result.data))
                        }
                    }
                }
        }
    }
}
