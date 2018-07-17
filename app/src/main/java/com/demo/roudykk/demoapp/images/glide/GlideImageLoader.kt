package com.demo.roudykk.demoapp.images.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.images.base.ImageLoader

object GlideImageLoader : ImageLoader {
    override fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.background_placeholder)
                .transforms(CenterCrop(), RoundedCorners(10))
                .into(imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Int, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .transforms(CircleCrop(), CenterCrop())
                .into(imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Drawable, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .into(imageView)
    }
}