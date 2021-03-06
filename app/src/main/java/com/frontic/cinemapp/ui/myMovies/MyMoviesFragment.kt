package com.frontic.cinemapp.ui.myMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.frontic.cinemapp.R
import com.frontic.cinemapp.adapters.ListMoviesAdapter
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseFragment

class MyMoviesFragment : BaseFragment(), MyMoviesContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: MyMoviesContract.Presenter
    private var genres: Map<Int, String>? = null
    private lateinit var emptyView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_movies, container, false)
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

        presenter = MyMoviesPresenter(this, requireContext())
        presenter.getMyMovies()

        recyclerView = view.findViewById(R.id.my_movies_rv)
        emptyView = view.findViewById(R.id.empty_list)
    }

    override fun showEmptyList(t: Boolean) {
        emptyView.visibility = if (t) View.VISIBLE else View.GONE
    }

    override fun showMyMovies(list: List<MovieListResult>) {
        recyclerView.adapter = context?.let { ListMoviesAdapter(it, list, genres, this) }
    }

    override fun goToMovie(movieListResult: MovieListResult) {
        val bundle = bundleOf("movie" to movieListResult)
        findNavController().navigate(
            R.id.action_navigation_my_movies_to_detailMovieFragment,
            bundle
        )
    }

    override fun showMessage(message: String) {
        showAlertMessage(message)
    }
}