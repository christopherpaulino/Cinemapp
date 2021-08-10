package com.frontic.cinemapp.models

import com.google.gson.annotations.SerializedName

/**
 * This data class represents the response structure of genres list request.
 *
 * @author Christopher Paulino.
 */
data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>

) {
    /**
     * Convert a list of Genres to a Map
     */
    fun toMap(): Map<Int, String> {
        val map: MutableMap<Int, String> = mutableMapOf()
        for (i in genres) {
            map[i.id] = i.name
        }
        return map
    }
}
