package com.frontic.cinemapp.ui.myMovies

import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseContract

class MyMoviesContract {

    interface Presenter {
        fun getMyMovies()
        fun destroy()
    }

    interface  View: BaseContract.BaseView {
        fun showMyMovies(list: List<MovieListResult>)
    }
}