package com.demo.roudykk.demoapp.images

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.demo.roudykk.demoapp.images.base.ImageLoader
import com.demo.roudykk.demoapp.images.glide.GlideImageLoader

object AppImageLoader : ImageLoader {
    private val curLoader: ImageLoader = GlideImageLoader

    override fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        curLoader.loadImage(context, imageUrl, imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Int, imageView: ImageView) {
        curLoader.loadImage(context, imageUrl, placeHolder, imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Drawable, imageView: ImageView) {
        curLoader.loadImage(context, imageUrl, placeHolder, imageView)
    }
}