package com.frontic.cinemapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * This class represents movie genres and also is an entity in the local DB
 * Created by Christopher Paulino
 */
@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
