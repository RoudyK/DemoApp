package com.demo.roudykk.demoapp.extensions

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.RatingBar
import com.demo.roudykk.demoapp.R

fun RatingBar.applyTheme() {
    val stars = progressDrawable as LayerDrawable
    stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, R.color.colorRating), PorterDuff.Mode.SRC_ATOP)
}

fun AppCompatActivity.applyPushAnimation() {
    overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left)
}

fun AppCompatActivity.applyPopAnimation() {
    overridePendingTransition(R.anim.fade_in_right, R.anim.fade_out_right)
}