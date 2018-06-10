package com.demo.roudykk.demoapp.util.images

import android.content.Context
import android.widget.ImageView
import com.demo.roudykk.demoapp.util.images.base.ImageLoader
import com.demo.roudykk.demoapp.util.images.glide.GlideImageLoader

object AppImageLoader : ImageLoader {
    private val curLoader: ImageLoader = GlideImageLoader

    override fun loadUserImage(context: Context, imageUrl: String, imageView: ImageView) {
        curLoader.loadUserImage(context, imageUrl, imageView)
    }

    override fun loadPostImage(context: Context, imageUrl: String, imageView: ImageView) {
        curLoader.loadPostImage(context, imageUrl, imageView)
    }

}