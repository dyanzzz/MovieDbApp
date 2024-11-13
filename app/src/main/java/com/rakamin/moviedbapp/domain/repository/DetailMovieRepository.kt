package com.rakamin.moviedbapp.domain.repository

import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    fun getDetailMovie(movieId: String): Flow<ResponseResult<DetailMovieResponse>>
}
