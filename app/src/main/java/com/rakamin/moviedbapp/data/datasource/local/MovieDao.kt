package com.rakamin.moviedbapp.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rakamin.moviedbapp.domain.model.table.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :id)")
    suspend fun isBookmarked(id: String): Boolean

    @Insert
    suspend fun addMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)
}
