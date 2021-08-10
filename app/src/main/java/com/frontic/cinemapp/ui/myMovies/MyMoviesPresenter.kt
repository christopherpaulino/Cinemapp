package com.frontic.cinemapp.ui.myMovies

import android.content.Context
import com.frontic.cinemapp.data.AppDatabase
import com.frontic.cinemapp.models.MovieListResult
import kotlinx.coroutines.*

/**
 * Presenter that interacts with MyMovies Fragment
 * @param view instance of MyMoviesContract.View interface that represents the View.
 * @param context Context of the View
 * @author Christopher Paulino
 */
class MyMoviesPresenter(private val view: MyMoviesContract.View, private val context: Context) : MyMoviesContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)

    override fun getMyMovies() {
        scopeMainThread.launch {
            val list = getMyMoviesDB()
            if (list.isNotEmpty()) {
                view.showMyMovies(list) 
                view.showEmptyList(false)
            } else (view.showEmptyList(true))

        }
    }

    override fun destroy() {
        job.cancel()
    }

    /**
     * This suspend function gets a list of movies from Room DB.
     * @return List of movies from Room DB.
     */
    private suspend fun getMyMoviesDB() : List<MovieListResult>{
        return withContext(Dispatchers.IO){
            return@withContext AppDatabase.getInstance(context).movieListResultDAO.getMyMovies()
        }
    }
}