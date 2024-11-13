package com.rakamin.moviedbapp.data.datasource.remote

import com.rakamin.moviedbapp.BuildConfig
import com.rakamin.moviedbapp.domain.model.BaseResponse
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    companion object {
        const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(): BaseResponse<MovieResponse>

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(): BaseResponse<MovieResponse>

    @GET("movie/{movieId}?api_key=$API_KEY")
    suspend fun getDetailMovie(
        @Path("movieId") movieId: String
    ): DetailMovieResponse
}
