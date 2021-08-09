package com.frontic.cinemapp.ui.detailMovie

import com.frontic.cinemapp.models.MovieListResult

class DetailsMovieContrat {

    interface Presenter {
        fun saveMovie(movieListResult: MovieListResult)
        fun deleteMovie(movieListResult: MovieListResult)
        fun checkIfExist(id: String)
    }

    interface View {
        fun onDeletedItem()
    }

}