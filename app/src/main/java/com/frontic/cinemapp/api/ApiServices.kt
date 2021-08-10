package com.frontic.cinemapp.api

import com.frontic.cinemapp.models.GenreResponse
import com.frontic.cinemapp.models.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * List of Api Services.
 *
 * @author Christopher Paulino.
 */
interface ApiServices {

    /**
     * Get list of trending movies from The MovieDB.
     */
    @GET("trending/{media_type}/{time_window}")
    fun getTrendingMovies(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("language") language: String
    ): Call<ListResponse>

    /**
     * Get list of genres available from The MovieDB.
     */
    @GET("genre/movie/list")
    fun getGenres(
        @Query("language") language: String
    ): Call<GenreResponse>

}