package com.frontic.cinemapp.api

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import java.io.File

/**
 * This class manage all the loads of images using Glide, allows you to display images from
 * different sources such as: URL, FILE o just return the Bitmap value from a URL.
 *
 * @param  context Application Context.
 * @author Christopher Paulino.
 */
class GlideApi(private val context: Context) {

    //Base URL of TheMovieDB API
    private val baseURL = "https://image.tmdb.org/t/p/"

    /**
     * Allows to load an image from a URL.
     *
     * @param urlPath       image name that is a reference of file location.
     * @param size [Size]   instance that reference the image size depending of what image you will load.
     * @param view          View where the image will be loaded.
     */
    @Suppress("DEPRECATION")
    fun loadImageFromUrl(
        urlPath: String,
        size: Size,
        view: ImageView
    ): ViewTarget<ImageView, Drawable> {
        return Glide.with(context).load(baseURL + size.value + urlPath).fitCenter().into(view)
    }

    /**
     * Allows to load an image from a URI getting its [File].
     *
     * @param urlPath   image name that is a reference of file location.
     * @param view      View where the image will be loaded.
     */
    @Suppress("DEPRECATION")
    fun loadImageFromUri(urlPath: String, view: ImageView): ViewTarget<ImageView, Drawable> {

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        file = File(file, urlPath)
        return Glide.with(context).load(file).fitCenter().into(view)
    }

    /**
     * Gets the bitmap file from an URL, used for downloading it.
     *
     * @param urlPath       image name that is a reference of file location.
     * @param size [Size]   instance that reference the image size depending of what image you will load.
     */
     fun getImageBitmap(urlPath: String, size: Size): Bitmap {
        return Glide.with(context).asBitmap()
            .load(baseURL + size.value + urlPath)
            .submit()
            .get()
    }

    enum class Size(val value: String) {
        Poster("w200/"),
        Backdrop("w1280")
    }
}