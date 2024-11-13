package com.rakamin.moviedbapp.data.repository

import com.rakamin.moviedbapp.data.datasource.remote.AppService
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import com.rakamin.moviedbapp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val appService: AppService
): MovieRepository {
    override fun getNowPlayingMovies(): Flow<ResponseResult<List<MovieResponse>>> {
        return flow {
            try {
                val response = appService.getNowPlayingMovies()
                response.results.let { result ->
                    if (result.isEmpty()) {
                        emit(ResponseResult.Loading)
                    } else {
                        emit(ResponseResult.Success(result))
                    }
                }
            } catch (e: Exception) {
                emit(ResponseResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMoviePopular(): Flow<ResponseResult<List<MovieResponse>>> {
        return flow {
            try {
                val response = appService.getPopularMovies()
                response.results.let { result ->
                    if (result.isEmpty()) {
                        emit(ResponseResult.Loading)
                    } else {
                        emit(ResponseResult.Success(result))
                    }
                }
            } catch (e: Exception) {
                emit(ResponseResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}
