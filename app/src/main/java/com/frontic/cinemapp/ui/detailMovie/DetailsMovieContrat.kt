package com.frontic.cinemapp.ui.detailMovie

import android.graphics.Bitmap
import com.frontic.cinemapp.models.MovieListResult

class DetailsMovieContrat {

    interface Presenter{
        fun saveMovie(movieListResult: MovieListResult)
    }

    interface View {

    }

}