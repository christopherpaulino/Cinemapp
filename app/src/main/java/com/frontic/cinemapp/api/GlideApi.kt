package com.frontic.cinemapp.api

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ViewTarget
import java.io.File

class GlideApi(private val context: Context) {
    val BASE_URL = "https://image.tmdb.org/t/p/"

    fun loadImageFromUrl(urlPath: String, size: Size, view: ImageView): ViewTarget<ImageView, Drawable> {
        return Glide.with(context).load(BASE_URL + size.value + urlPath).fitCenter().into(view)
    }

    fun loadImageFromUri(urlPath: String, view: ImageView): ViewTarget<ImageView, Drawable> {

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        file = File(file, urlPath)
        return Glide.with(context).load(file).fitCenter().into(view)
    }

    suspend fun getImageBitmat(urlPath: String, size: Size): Bitmap {
        return Glide.with(context).asBitmap()
            .load(BASE_URL + size.value + urlPath)
            .submit()
            .get()
    }

    enum class Size(val value: String) {
        poster("w200/"),
        backdrop("w1280")
    }
}