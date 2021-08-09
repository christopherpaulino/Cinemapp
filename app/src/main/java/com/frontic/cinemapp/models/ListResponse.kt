package com.frontic.cinemapp.models

import com.google.gson.annotations.SerializedName

/**
 * This data class represents the response structure of movies list request.
 * Created by Christopher Paulino
 */
data class ListResponse(
    @SerializedName("page")
    val page: Long,
    @SerializedName("results")
    val results: List<MovieListResult>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)