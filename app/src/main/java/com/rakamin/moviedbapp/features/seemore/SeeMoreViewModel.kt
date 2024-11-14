package com.rakamin.moviedbapp.features.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import com.rakamin.moviedbapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _playingMovies = MutableStateFlow<ResponseResult<List<MovieResponse>>>(ResponseResult.Loading)
    var listPlayingMovies = _playingMovies.asLiveData()
    fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.getNowPlayingMovies()
                .catch { 
                    println(it.message)
                }.collectLatest { result -> 
                    when (result) {
                        is ResponseResult.Loading -> {
                            _playingMovies.emit(ResponseResult.Loading)
                        }
                        is ResponseResult.Error -> {
                            _playingMovies.emit(ResponseResult.Error(result.throwable))
                        }
                        is ResponseResult.Success -> {
                            println("testing data ${result.data}")
                            _playingMovies.emit(ResponseResult.Success(result.data))
                        }
                    }
                }
        }
    }
    
    private val _popularMovies = MutableStateFlow<ResponseResult<List<MovieResponse>>>(ResponseResult.Loading)
    var listPopularMovies = _popularMovies.asLiveData()
    fun getMoviePopular() {
        viewModelScope.launch {
            movieRepository.getMoviePopular()
                .catch {
                    println(it.message)
                }
                .collectLatest { result ->
                    when (result) {
                        is ResponseResult.Loading -> {
                            _popularMovies.emit(ResponseResult.Loading)
                        }
                        is ResponseResult.Error -> {
                            _popularMovies.emit(ResponseResult.Error(result.throwable))
                        }
                        is ResponseResult.Success -> {
                            _popularMovies.emit(ResponseResult.Success(result.data))
                        }
                    }
                }
        }
    }
}
