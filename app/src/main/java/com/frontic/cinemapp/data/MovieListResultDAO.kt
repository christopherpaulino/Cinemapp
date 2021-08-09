package com.frontic.cinemapp.data

import androidx.room.*
import com.frontic.cinemapp.models.MovieListResult

@Dao
interface MovieListResultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieListResult)

    @Delete
    suspend fun delete(movie: MovieListResult)

    @Update
    suspend fun update(movie: MovieListResult)

    @Query("SELECT * FROM my_movies")
    suspend fun getMyMovies(): List<MovieListResult>

    @Query("select * from my_movies where id = :id")
    suspend fun getMovieById(id: Int): MovieListResult

    @Query("delete from my_movies")
    suspend fun clear()
}