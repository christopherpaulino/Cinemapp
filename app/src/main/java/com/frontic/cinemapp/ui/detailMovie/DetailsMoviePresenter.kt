package com.frontic.cinemapp.ui.detailMovie

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import com.frontic.cinemapp.api.GlideApi
import com.frontic.cinemapp.data.AppDatabase
import com.frontic.cinemapp.models.Location
import com.frontic.cinemapp.models.MovieListResult
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

/**
 * Presenter that interacts with [DetailMovieFragment].
 *
 * @param view      instance of [DetailsMovieContract.View] interface that represents the View.
 * @param context   Context of the View.
 * @author          Christopher Paulino.
 */
class DetailsMoviePresenter(
    private val view: DetailsMovieContract.View,
    private val context: Context
) :
    DetailsMovieContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)

    override fun saveMovie(movieListResult: MovieListResult) {
        scopeMainThread.launch {
            saveMovieDB(movieListResult)
            view.setSaveButtonVisibility(false)
        }
    }

    private suspend fun saveMovieDB(movieListResult: MovieListResult) {
        return withContext(Dispatchers.IO) {

            movieListResult.location = Location.Local
            AppDatabase.getInstance(context).movieListResultDAO.insert(movieListResult)
            saveImage(movieListResult.posterPath, GlideApi.Size.Poster)
            saveImage(movieListResult.backdropPath, GlideApi.Size.Backdrop)
        }
    }

    override fun deleteMovie(movieListResult: MovieListResult) {
        scopeMainThread.launch {
            deleteMovieDB(movieListResult)
            view.onDeletedItem()
        }
    }

    private suspend fun deleteMovieDB(movieListResult: MovieListResult) {
        return withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).movieListResultDAO.delete(movieListResult)
        }
    }

    override fun checkIfExist(id: Int) {
        scopeMainThread.launch {

            view.setSaveButtonVisibility(!checkIfExistInDB(id))
        }
    }

    private suspend fun checkIfExistInDB(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val movie = AppDatabase.getInstance(context).movieListResultDAO.getMovieById(id)
            return@withContext (movie != null)
        }
    }

    private fun saveImage(path: String, size: GlideApi.Size) {
        val bitmap = GlideApi(context).getImageBitmap(path, size)

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        file = File(file, path.replace("/", ""))

        try {
            val stream: OutputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            stream.flush()

            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}