package com.frontic.cinemapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "my_movies")
data class MovieListResult(
    @SerializedName("genre_ids")
    @ColumnInfo(name="genre_ids")
    val genreIDS: List<Int>,

    @PrimaryKey
    val id: Long,


    @SerializedName("original_title")
    @ColumnInfo(name="original_title")
    val originalTitle: String? = null,

    @SerializedName("poster_path")
    @ColumnInfo(name="poster_path")
    val posterPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val overview: String,


    @SerializedName("vote_count")
    @ColumnInfo(name= "vote_count")
    val voteCount: Long,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    val title: String? = null,


    @SerializedName("media_type")
    @ColumnInfo(name="media_type")
    val mediaType: String,

    @SerializedName("original_name")
    @ColumnInfo(name="original_name")
    val originalName: String? = null,

    @SerializedName("origin_country")
    @ColumnInfo(name = "origin_country")
    val originCountry: List<String>? = null,): Serializable{

    var location: Location? = Location.remote

    fun getGenresString(genres: Map<Int,String>):String{
        val strings = ArrayList<String>()

        genreIDS.forEach { strings.add(genres[it] ?: error("")) }

        return strings.joinToString() }

    override fun toString(): String {
        return "MovieListResult(genreIDS=$genreIDS, id=$id, originalTitle=$originalTitle, posterPath='$posterPath', voteAverage=$voteAverage, overview='$overview', voteCount=$voteCount, backdropPath='$backdropPath', title=$title, mediaType='$mediaType', originalName=$originalName, originCountry=$originCountry, location=$location)"
    }


}

enum class Location {
    local, remote
}

