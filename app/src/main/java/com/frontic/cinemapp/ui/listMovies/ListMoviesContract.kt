package com.frontic.cinemapp.ui.listMovies

import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseContract

/**
 * Contract with the interfaces to be implemented by ListMovies View and Presenter
 * according to MVP Pattern.
 */
class ListMoviesContract {

    interface Presenter {
        fun getTrending(mediaType: String)
        fun getGenres()
        fun destroy()
    }

    interface View : BaseContract.BaseView {
        fun showList(list: List<MovieListResult>)
        fun showGenres(genres: Map<Int, String>)
        fun showLoading(show: Boolean)
        fun showNoNetworkConnected(t: Boolean)
    }
}