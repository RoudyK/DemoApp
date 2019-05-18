@file:Suppress("unused")

package com.demo.roudykk.demoapp.databinding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.demo.roudykk.demoapp.images.AppImageLoader

/**
 * Sets an [ImageView] src using [AppImageLoader]
 *
 * @param imageUrl the url of the image
 * @param placeHolder optional, the place holder drawable id
 */
@BindingAdapter(value = ["app:imageUrl", "app:placeHolder"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String, placeHolder: Drawable?) {
    if (placeHolder != null) {
        AppImageLoader.loadImage(context, imageUrl, placeHolder, this)
    } else {
        AppImageLoader.loadImage(context, imageUrl, this)
    }
}

@BindingAdapter("app:visibleUnless")
fun View.visibleUnless(hide: Boolean) {
    visibility = if (hide) View.GONE else View.VISIBLE
}

@BindingAdapter("app:goneUnless")
fun View.goneUnless(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:onClickListener")
fun View.onClickListener(onClickListener: View.OnClickListener?) {
    setOnClickListener(onClickListener)
}
