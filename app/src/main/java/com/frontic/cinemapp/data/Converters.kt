package com.frontic.cinemapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * This class manage all [TypeConverter] needed for reading or writing in the database.
 *
 * @author Christopher Paulino.
 */
class Converters {

    @TypeConverter
    fun fromStringToArrayListString(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIntArrayList(list: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToArrayListInt(value: String?): List<Int?>? {
        val listType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringArrayList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}