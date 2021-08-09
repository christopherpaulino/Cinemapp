package com.frontic.cinemapp.ui.detailMovie

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.frontic.cinemapp.R
import com.frontic.cinemapp.api.GlideApi
import com.frontic.cinemapp.models.Location
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseFragment


class DetailMovieFragment : BaseFragment(), DetailsMovieContrat.View {

    lateinit var movieListResult: MovieListResult
    lateinit var title: TextView
    lateinit var ratingBar: RatingBar
    lateinit var ratingText: TextView
    lateinit var backdrop: ImageView
    lateinit var poster: ImageView
    lateinit var overview: TextView
    lateinit var originalTitle: TextView
    lateinit var saveButton: Button
    lateinit var presenter: DetailsMovieContrat.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        movieListResult = arguments?.get("movie") as MovieListResult
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root =  inflater.inflate(R.layout.fragment_detail_movie, container, false)
        initializeVariables(root)
        loadData()
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
    }

    private fun loadData() {
        movieListResult.let {
            title.text = it.title
            ratingText.text = "${it.voteAverage}"
            ratingBar.rating = (it.voteAverage / 2).toFloat()
            overview.text = it.overview
            originalTitle.text = it.originalTitle

            if (movieListResult.location == Location.local){
                GlideApi(requireContext()).loadImageFromUri(it.posterPath,poster)
                GlideApi(requireContext()).loadImageFromUri(it.backdropPath,backdrop)
            } else {
                GlideApi(requireContext()).loadImageFromUrl(it.posterPath,GlideApi.Size.poster,poster)
                GlideApi(requireContext()).loadImageFromUrl(it.backdropPath,GlideApi.Size.backdrop,backdrop)
            }

            saveButton.setOnClickListener{
                presenter.saveMovie(movieListResult)
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }


}