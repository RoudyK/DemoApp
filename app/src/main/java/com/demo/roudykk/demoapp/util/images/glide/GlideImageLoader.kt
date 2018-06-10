package com.demo.roudykk.demoapp.util.images.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.util.images.base.ImageLoader

object GlideImageLoader : ImageLoader {

    override fun loadUserImage(context: Context, imageUrl: String, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder_circle)
                .transforms(CircleCrop(), CenterCrop())
                .into(imageView)
    }

    override fun loadPostImage(context: Context, imageUrl: String, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.background_post_image)
                .transforms(CenterCrop(), RoundedCorners(10))
                .into(imageView)
    }

}