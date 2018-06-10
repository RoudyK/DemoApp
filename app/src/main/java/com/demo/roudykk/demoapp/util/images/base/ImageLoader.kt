package com.demo.roudykk.demoapp.util.images.base

import android.content.Context
import android.widget.ImageView

interface ImageLoader {

    fun loadPostImage(context: Context, imageUrl: String, imageView: ImageView)

    fun loadUserImage(context: Context, imageUrl: String, imageView: ImageView)
}
