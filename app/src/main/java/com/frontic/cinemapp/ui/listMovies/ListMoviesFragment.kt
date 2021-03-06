package com.frontic.cinemapp.ui.listMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.frontic.cinemapp.R
import com.frontic.cinemapp.adapters.ListMoviesAdapter
import com.frontic.cinemapp.models.MediaType
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseFragment

class ListMoviesFragment : BaseFragment(), ListMoviesContract.View {

    private lateinit var presenter: ListMoviesPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var genres: Map<Int, String>
    private lateinit var progressBar: ProgressBar
    private lateinit var noNetworkView: View
    private lateinit var swipeRefresher: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_movies, container, false)
        initializeVariables(root)
        return root
    }

    override fun onDetach() {
        super.onDetach()
        presenter.destroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun initializeVariables(view: View) {
        super.initializeVariables(view)
        presenter = ListMoviesPresenter(this, requireContext())

        swipeRefresher = view.findViewById(R.id.swipe_refresh)
        progressBar = view.findViewById(R.id.loading_pbar)
        recyclerView = view.findViewById(R.id.list_movies_rv)
        noNetworkView = view.findViewById(R.id.no_internet_screen)

        swipeRefresher.setOnRefreshListener {
            presenter.getGenres()
        }
        presenter.getGenres()
    }

    override fun showGenres(genres: Map<Int, String>) {
        this.genres = genres
        presenter.getTrending(MediaType.movie.name)
    }

    override fun showLoading(show: Boolean) {
        swipeRefresher.isRefreshing = show
    }

    override fun showList(list: List<MovieListResult>) {
        recyclerView.adapter = context?.let { context ->
            ListMoviesAdapter(
                context,
                list.sortedByDescending { it.voteAverage },
                genres,
                this
            )
        }
    }

    override fun goToMovie(movieListResult: MovieListResult) {
        val bundle = bundleOf("movie" to movieListResult)
        findNavController().navigate(
            R.id.action_navigation_list_movies_to_detailMovieFragment,
            bundle
        )
    }

    override fun showNoNetworkConnected(t: Boolean) {
        recyclerView.visibility = if (t) View.GONE else View.VISIBLE
        noNetworkView.visibility = if (t) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        showAlertMessage(message)
    }
}