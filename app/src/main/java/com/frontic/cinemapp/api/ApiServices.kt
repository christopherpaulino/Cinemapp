package com.frontic.cinemapp.api

import com.frontic.cinemapp.models.GenreResponse
import com.frontic.cinemapp.models.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {


    @GET("trending/{media_type}/{time_window}?language=es-MX")
    fun getTrendingMovies(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String
    ): Call<ListResponse>

    @GET("genre/movie/list")
    fun getGenres():Call<GenreResponse>

}