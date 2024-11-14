package com.rakamin.moviedbapp.data.repository.local

import com.rakamin.moviedbapp.data.datasource.local.MovieDao
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.domain.model.table.Movie
import com.rakamin.moviedbapp.domain.repository.local.MovieLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalRepositoryImpl @Inject constructor(
    private val dao: MovieDao
): MovieLocalRepository {
    override suspend fun getMovies() = dao.getMovies()

    override suspend fun isBookmarked(movieId: String): Boolean {
        return dao.isBookmarked(movieId)
    }

    override suspend fun setBookmark(movieResponse: DetailMovieResponse, isBookmark: Boolean) {
        val movie = Movie(
            id = movieResponse.id ?: 0,
            imdbId = movieResponse.imdbId,
            title = movieResponse.title,
            overview = movieResponse.overview,
            posterPath = movieResponse.posterPath,
            backdropPath = movieResponse.backdropPath,
            releaseDate = movieResponse.releaseDate,
            popularity = movieResponse.popularity,
            voteAverage = movieResponse.voteAverage,
            voteCount = movieResponse.voteCount?.toDouble(),
            status = movieResponse.status,
        )
        if (!isBookmark) dao.addMovie(movie) else dao.deleteMovie(movie)
    }
}
