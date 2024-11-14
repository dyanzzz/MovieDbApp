package com.rakamin.moviedbapp.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.domain.repository.DetailMovieRepository
import com.rakamin.moviedbapp.domain.repository.local.MovieLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository,
    private val movieLocalRepository: MovieLocalRepository
): ViewModel() {
    private val _detailMovie = MutableStateFlow<ResponseResult<DetailMovieResponse>>(ResponseResult.Loading)
    var detailMovie = _detailMovie.asLiveData()
    fun getDetailMovie(movieId: String) {
        viewModelScope.launch {
            detailMovieRepository.getDetailMovie(movieId)
                .catch { _detailMovie.emit(ResponseResult.Error(it)) }
                .collectLatest { result ->
                    println(result.toString())
                    when (result) {
                        is ResponseResult.Loading -> {
                            _detailMovie.emit(ResponseResult.Loading)
                        }
                        is ResponseResult.Error -> {
                            _detailMovie.emit(ResponseResult.Error(result.throwable))
                        }
                        is ResponseResult.Success -> {
                            _detailMovie.emit(ResponseResult.Success(result.data))
                        }
                    }
                }
        }
    }

    private var _isBookmark = MutableStateFlow(false)
    val isBookmark = _isBookmark.asLiveData()
    fun isBookmarked(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isBookmark = movieLocalRepository.isBookmarked(movieId)
            _isBookmark.emit(isBookmark)
        }
    }

    fun setBookmark(movie: DetailMovieResponse, isBookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            movieLocalRepository.setBookmark(movie, isBookmark)
        }
    }
}
