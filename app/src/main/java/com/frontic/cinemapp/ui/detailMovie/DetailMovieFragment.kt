package com.frontic.cinemapp.ui.detailMovie

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.frontic.cinemapp.R
import com.frontic.cinemapp.api.GlideApi
import com.frontic.cinemapp.models.Location
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseFragment


class DetailMovieFragment : BaseFragment(), DetailsMovieContract.View {

    private lateinit var movieListResult: MovieListResult
    private lateinit var title: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ratingText: TextView
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var overview: TextView
    private lateinit var originalTitle: TextView
    private lateinit var saveButton: Button
    private lateinit var presenter: DetailsMovieContract.Presenter
    private lateinit var deleteButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        movieListResult = arguments?.get("movie") as MovieListResult
        Log.i("Movie",movieListResult.toString())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root =  inflater.inflate(R.layout.fragment_detail_movie, container, false)
        initializeVariables(root)
        loadData()
        return root
    }

    override fun initializeVariables(view: View) {
        super.initializeVariables(view)
        presenter = DetailsMoviePresenter(this,requireContext())

        title = view.findViewById(R.id.title_tv)
        ratingText = view.findViewById(R.id.rating_tv)
        ratingBar = view.findViewById(R.id.rating_bar)
        poster = view.findViewById(R.id.poster_iv)
        backdrop = view.findViewById(R.id.backdrop)
        overview = view.findViewById(R.id.overview_tv)
        originalTitle = view.findViewById(R.id.original_title_tv)
        saveButton = view.findViewById(R.id.save_btn)
        deleteButton = view.findViewById(R.id.delete_btn)
    }

    private fun loadData() {
        movieListResult.let {
            title.text = it.title
            ratingText.text = "${it.voteAverage}"
            ratingBar.rating = (it.voteAverage / 2).toFloat()
            overview.text = it.overview
            originalTitle.text = it.originalTitle

            if (movieListResult.location == Location.Local){
                manageLocalData(it)
            } else {
                manageRemoteData(it)
            }
        }
    }

    private fun manageLocalData(movieListResult: MovieListResult) {
            deleteButton.visibility = View.VISIBLE
            deleteButton.setOnClickListener { presenter.deleteMovie(movieListResult) }

            GlideApi(requireContext()).loadImageFromUri(movieListResult.posterPath,poster)
            GlideApi(requireContext()).loadImageFromUri(movieListResult.backdropPath,backdrop)
    }

    private fun manageRemoteData(movieListResult: MovieListResult) {
        saveButton.visibility = View.VISIBLE
        saveButton.setOnClickListener{ presenter.saveMovie(movieListResult) }

        GlideApi(requireContext()).loadImageFromUrl(movieListResult.posterPath,GlideApi.Size.Poster,poster)
        GlideApi(requireContext()).loadImageFromUrl(movieListResult.backdropPath,GlideApi.Size.Backdrop,backdrop)
    }

    override fun onDeletedItem() {
        findNavController().navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)

    }

}