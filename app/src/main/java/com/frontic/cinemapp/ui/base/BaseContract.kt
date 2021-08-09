package com.frontic.cinemapp.ui.base

import com.frontic.cinemapp.models.MovieListResult

class BaseContract {

    interface BaseView{
        fun goToMovie(movieListResult: MovieListResult)
    }
}