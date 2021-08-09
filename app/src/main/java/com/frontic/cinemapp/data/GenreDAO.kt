package com.frontic.cinemapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontic.cinemapp.models.Genre

@Dao
interface GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (genre: Genre)

    @Query("select * from genre")
    suspend fun getGenres(): List<Genre>
}