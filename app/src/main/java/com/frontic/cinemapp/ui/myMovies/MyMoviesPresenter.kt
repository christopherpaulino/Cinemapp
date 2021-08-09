package com.frontic.cinemapp.ui.myMovies

import android.content.Context
import com.frontic.cinemapp.data.AppDatabase
import com.frontic.cinemapp.models.MovieListResult
import kotlinx.coroutines.*

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

    private suspend fun getMyMoviesDB() : List<MovieListResult>{
        return withContext(Dispatchers.IO){
            return@withContext AppDatabase.getInstance(context).movieListResultDAO.getMyMovies()
        }
    }
}