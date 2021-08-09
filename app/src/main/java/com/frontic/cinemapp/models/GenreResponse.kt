package com.frontic.cinemapp.models

import android.util.Log
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>

) {
    fun toMap(): Map<Int, String> {
        val map:MutableMap<Int,String> = mutableMapOf()
        for (i in genres) {
            map[i.id] = i.name
        }
        return map
    }
}
