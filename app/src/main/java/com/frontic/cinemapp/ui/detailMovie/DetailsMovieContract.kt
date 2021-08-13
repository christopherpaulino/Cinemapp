package com.frontic.cinemapp.ui.detailMovie

import com.frontic.cinemapp.models.MovieListResult

/**
 * Contract with the interfaces to be implemented by DetailsMovie View and Presenter
 * according to MVP Pattern.
 */
class DetailsMovieContract {

    interface Presenter {
        fun saveMovie(movieListResult: MovieListResult)
        fun deleteMovie(movieListResult: MovieListResult)
        fun checkIfExist(id: Int)
    }

    interface View {
        fun onDeletedItem()
        fun setSaveButtonVisibility(show: Boolean)
    }

}