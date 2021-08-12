package com.frontic.cinemapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frontic.cinemapp.R
import com.frontic.cinemapp.api.GlideApi
import com.frontic.cinemapp.models.MovieListResult
import com.frontic.cinemapp.ui.base.BaseContract

/**
 * Adapter to display movie list, both local and remote ones.
 *
 * @param context       Application context.
 * @param listItems     List of the [MovieListResult] elements to be loaded.
 * @param genres        Map containing genres to be shown depending its value.
 * @param view          instance of the View.
 * @author Christopher Paulino.
 */
class ListMoviesAdapter(
    private val context: Context,
    private val listItems: List<MovieListResult>,
    private val genres: Map<Int, String>?,
    private val view: BaseContract.BaseView
) :
    RecyclerView.Adapter<ListMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.movie_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        holder.apply {
            title.text = item.title
            ratingText.text = "${item.voteAverage}"
            ratingBar.rating = (item.voteAverage / 2).toFloat()

            votes.text = (context.getString(R.string.votes_text)).replace(
                context.getString(R.string.votes_placeholder),
                "${item.voteCount}"
            )

            GlideApi(context).loadImageFromUrl(item.posterPath, GlideApi.Size.Poster, poster)

            if (!genres.isNullOrEmpty()) {
                holder.genresList.text = item.getGenresString(genres)
            }

            holder.itemView.setOnClickListener {
                view.goToMovie(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    /**
     * View Holder that represent a movie list item.
     *
     * @param view View resource.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_tv)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val ratingText: TextView = view.findViewById(R.id.rating_tv)
        val votes: TextView = view.findViewById(R.id.votes_tv)
        val poster: ImageView = view.findViewById(R.id.poster_iv)
        val genresList: TextView = view.findViewById(R.id.genres_tv)
    }
}