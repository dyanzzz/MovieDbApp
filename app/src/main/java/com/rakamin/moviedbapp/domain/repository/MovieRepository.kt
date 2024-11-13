package com.rakamin.moviedbapp.domain.repository

import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(): Flow<ResponseResult<List<MovieResponse>>>
    fun getMoviePopular(): Flow<ResponseResult<List<MovieResponse>>>
}
