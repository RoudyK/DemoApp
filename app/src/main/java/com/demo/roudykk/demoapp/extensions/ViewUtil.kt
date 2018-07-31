package com.demo.roudykk.demoapp.extensions

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.RatingBar
import android.widget.TextView
import android.widget.TextView.BufferType
import com.demo.roudykk.demoapp.R
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View


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