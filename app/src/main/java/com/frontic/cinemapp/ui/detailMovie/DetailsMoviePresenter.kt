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

class DetailsMoviePresenter(
    private val view: DetailsMovieContrat.View,
    private val context: Context
) :
    DetailsMovieContrat.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun saveMovie(movieListResult: MovieListResult) {
        scopeMainThread.launch {
            saveMovieDB(movieListResult)
        }
    }

    private suspend fun saveMovieDB(movieListResult: MovieListResult): Int {
        return withContext(Dispatchers.IO) {
            movieListResult.location = Location.local
            AppDatabase.getInstance(context).movieListResultDAO.insert(movieListResult)
            saveImage(movieListResult.posterPath, GlideApi.Size.poster)
            saveImage(movieListResult.backdropPath, GlideApi.Size.backdrop)

            val list = AppDatabase.getInstance(context).movieListResultDAO.getMyMovies()
            return@withContext list.size
        }
    }

    override fun deleteMovie(movieListResult: MovieListResult) {
        scopeMainThread.launch {
            deleteMovieDB(movieListResult)
            view.onDeletedItem()
        }
    }

    private suspend fun deleteMovieDB (movieListResult: MovieListResult){
        return withContext(Dispatchers.IO){
            AppDatabase.getInstance(context).movieListResultDAO.delete(movieListResult)
        }
    }

    override fun checkIfExist(id: String) {

    }

    private suspend fun saveImage(path: String, size: GlideApi.Size) {
        val bitmap = GlideApi(context).getImageBitmat(path, size)

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

        Log.i("uri", file.absolutePath)
    }
}