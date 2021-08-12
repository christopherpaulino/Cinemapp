package com.frontic.cinemapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frontic.cinemapp.models.Genre
import com.frontic.cinemapp.models.MovieListResult

/**
 * Application Database which storage all the local data.
 *
 * @author Christopher Paulino.
 */
@Database(entities = [MovieListResult::class, Genre::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieListResultDAO: MovieListResultDAO
    abstract val genreDao: GenreDAO

    /**
     * Manage a singleton instance of the database.
     */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance
        }
    }
}