package com.rakamin.moviedbapp.domain.repository.local

import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.domain.model.table.Movie

interface MovieLocalRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun isBookmarked(movieId: String): Boolean
    suspend fun setBookmark(movieResponse: DetailMovieResponse, isBookmark: Boolean)
}
