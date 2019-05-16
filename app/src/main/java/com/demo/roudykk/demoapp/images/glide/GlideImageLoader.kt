package com.demo.roudykk.demoapp.images.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.images.base.ImageLoader

object GlideImageLoader : ImageLoader {
    override fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.placeHolder, typedValue, true)
        GlideApp.with(context)
                .load(imageUrl)
                .transform(CenterCrop(), RoundedCorners(20))
                .transition(withCrossFade())
                .placeholder(typedValue.resourceId)
                .into(imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Int, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .transform(CenterCrop(), RoundedCorners(20))
                .transition(withCrossFade())
                .placeholder(placeHolder)
                .into(imageView)
    }

    override fun loadImage(context: Context, imageUrl: String, placeHolder: Drawable, imageView: ImageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .transform(CenterCrop(), RoundedCorners(20))
                .transition(withCrossFade())
                .placeholder(placeHolder)
                .into(imageView)
    }
}