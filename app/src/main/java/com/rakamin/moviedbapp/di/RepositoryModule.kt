package com.rakamin.moviedbapp.di

import com.rakamin.moviedbapp.data.datasource.remote.AppService
import com.rakamin.moviedbapp.data.repository.DetailMovieRepositoryImpl
import com.rakamin.moviedbapp.data.repository.MovieRepositoryImpl
import com.rakamin.moviedbapp.domain.repository.DetailMovieRepository
import com.rakamin.moviedbapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(appService: AppService): MovieRepository {
        return MovieRepositoryImpl(appService)
    }

    @Provides
    @Singleton
    fun provideDetailMovieRepository(appService: AppService): DetailMovieRepository {
        return DetailMovieRepositoryImpl(appService)
    }
}