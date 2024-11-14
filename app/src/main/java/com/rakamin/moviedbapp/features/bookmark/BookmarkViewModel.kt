package com.rakamin.moviedbapp.features.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakamin.moviedbapp.domain.model.table.Movie
import com.rakamin.moviedbapp.domain.repository.local.MovieLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
): ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val listMovies = _movies.asLiveData()
    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies = movieLocalRepository.getMovies()
            _movies.emit(listMovies)
        }
    }
}
