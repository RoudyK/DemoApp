package com.demo.roudykk.demoapp.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.demo.roudykk.demoapp.images.AppImageLoader

/**
 * Sets an [ImageView] src using [AppImageLoader]
 *
 * @param imageUrl the url of the image
 * @param placeHolder optional, the place holder drawable id
 */
@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String, placeHolder: Drawable?) {
    if (placeHolder != null) {
        AppImageLoader.loadImage(context, imageUrl, placeHolder, this)
    } else {
        AppImageLoader.loadImage(context, imageUrl, this)
    }
}