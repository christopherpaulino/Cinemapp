package com.frontic.cinemapp.ui.listMovies

import android.content.Context
import android.util.Log
import com.frontic.cinemapp.api.ApiRest
import com.frontic.cinemapp.data.AppDatabase
import com.frontic.cinemapp.models.Genre
import com.frontic.cinemapp.models.GenreResponse
import com.frontic.cinemapp.models.ListResponse
import com.frontic.cinemapp.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMoviesPresenter(private val view: ListMoviesContract.View, private val context: Context) :
    ListMoviesContract.Presenter {

    private val job = Job()
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun destroy() {
        job.cancel()
    }

    override fun getTrending(mediaType: String) {
        if (isNetworkConnected()) {
            ApiRest.create().getTrendingMovies(mediaType, "day")
                .enqueue(object : Callback<ListResponse> {
                    override fun onResponse(
                        call: Call<ListResponse>,
                        response: Response<ListResponse>
                    ) {
                        if (response.isSuccessful) {

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

    }

    override fun getGenres() {
        view.showLoading(true)
        if (isNetworkConnected()) {
            ApiRest.create().getGenres().enqueue(object : Callback<GenreResponse> {
                override fun onResponse(
                    call: Call<GenreResponse>,
                    response: Response<GenreResponse>
                ) {
                    if (response.isSuccessful) {
                        view.showGenres(response.body()!!.toMap())
                        saveGenres(response.body()!!.genres)
                    }
                }

                override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                    view.showLoading(false)
                }
            })
        }
    }

    private fun isNetworkConnected(): Boolean {
        return if (NetworkUtils.isConnected(context)) {
            view.showNoNetworkConnected(false)
            true
        } else {
            view.showLoading(false)
            view.showNoNetworkConnected(true)
            false
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