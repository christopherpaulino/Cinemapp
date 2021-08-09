package com.frontic.cinemapp.ui.listMovies

import com.frontic.cinemapp.models.Genre
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseContract

class ListMoviesContract {

    interface Presenter {
        fun getTrending(mediaType: String)
        fun getGenres()
        fun saveMovie (movieListResult: MovieListResult)
        fun destroy()
    }

    interface View : BaseContract.BaseView{
        fun showList(list:List<MovieListResult>)
        fun showTrending(value: String)
        fun showGenres(genres: Map<Int,String>)
        fun saveMovie(movieListResult: MovieListResult)
        fun showLoading(show: Boolean)
    }
}