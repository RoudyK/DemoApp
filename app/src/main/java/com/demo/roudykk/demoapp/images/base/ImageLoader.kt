package com.demo.roudykk.demoapp.images.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {

    fun loadImage(context: Context, imageUrl: String, imageView: ImageView)

    fun loadImage(context: Context, imageUrl: String, placeHolder: Int, imageView: ImageView)

    fun loadImage(context: Context, imageUrl: String, placeHolder: Drawable, imageView: ImageView)
}
