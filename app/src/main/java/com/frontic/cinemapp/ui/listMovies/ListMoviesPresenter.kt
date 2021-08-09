package com.frontic.cinemapp.ui.listMovies

import android.content.Context
import android.util.Log
import com.frontic.cinemapp.api.ApiRest
import com.frontic.cinemapp.data.AppDatabase
import com.frontic.cinemapp.models.Genre
import com.frontic.cinemapp.models.GenreResponse
import com.frontic.cinemapp.models.ListResponse
import com.frontic.cinemapp.models.MovieListResult
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMoviesPresenter(private val view: ListMoviesContract.View, private val context: Context) :
    ListMoviesContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun getTrending(mediaType: String) {
        view.showLoading(true)
        ApiRest.create().getTrendingMovies(mediaType, "day")
            .enqueue(object : Callback<ListResponse> {

                override fun onResponse(
                    call: Call<ListResponse>,
                    response: Response<ListResponse>
                ) {
                    if (response.isSuccessful) {

                        saveMovie(response.body()!!.results[2])
                        view.showList(response.body()!!.results)
                        view.showLoading(false)

                    }
                }

                override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                    Log.d("Request", t.message!!)
                    view.showLoading(false)
                }
            })
    }


    override fun getGenres() {

        ApiRest.create().getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    view.showGenres(response.body()!!.toMap())
                    saveGenres(response.body()!!.genres)
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun destroy() {
        job.cancel()
    }

    override fun saveMovie(movieListResult: MovieListResult) {

        scopeMainThread.launch {
            view.showTrending("${saveMovieDB(movieListResult)}")
        }
    }

    private suspend fun saveMovieDB(movieListResult: MovieListResult): Int {
        return withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).movieListResultDAO.insert(movieListResult)
            val list = AppDatabase.getInstance(context).movieListResultDAO.getMyMovies()
            return@withContext list.size
        }
    }

    private fun saveGenres(list: List<Genre>) {
        scopeIO.launch {
            for (i in list) {
                AppDatabase.getInstance(context).genreDao.insert(i)
            }
        }
    }
}
//    private fun saveMovieDB(movieListResult: MovieListResult){
//        scopeIO.launch {
//            AppDatabase.getInstance(context).movieListResultDAO.insert(movieListResult)
//            val list = AppDatabase.getInstance(context).movieListResultDAO.getMyMovies()
//
//            scopeMainThread.launch {
//                view.showTrending("${list.size}")
//            }
//        }
//    }
//}