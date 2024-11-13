package com.rakamin.moviedbapp.data.repository

import com.rakamin.moviedbapp.data.datasource.remote.AppService
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.domain.repository.DetailMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailMovieRepositoryImpl @Inject constructor(
    private val appService: AppService
): DetailMovieRepository {
    override fun getDetailMovie(movieId: String): Flow<ResponseResult<DetailMovieResponse>> {
        return flow {
            try {
                val response = appService.getDetailMovie(movieId)
                println("response $response")
                if (response == null) {
                    emit(ResponseResult.Loading)
                } else {
                    emit(ResponseResult.Success(response))
                }
            } catch (e: Exception) {
                println("error $e")
                emit(ResponseResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}
