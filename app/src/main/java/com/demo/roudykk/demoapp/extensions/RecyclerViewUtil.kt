package com.demo.roudykk.demoapp.extensions

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.ui.activity.MainActivity
import com.google.android.material.appbar.AppBarLayout
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Sets up default OverScroll behavior for [RecyclerView]
 */
fun RecyclerView.addOverScroll() {
    OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}

fun RecyclerView.withAppBar(appBarLayout: AppBarLayout?) {
    if (canScrollVertically(-1)) {
        appBarLayout?.elevation = context.resources.getDimension(R.dimen.app_bar_elevation)
    } else {
        appBarLayout?.elevation = 0F
    }
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (canScrollVertically(-1)) {
                appBarLayout?.elevation = context.resources.getDimension(R.dimen.app_bar_elevation)
            } else {
                appBarLayout?.elevation = 0F
            }
        }
    })
}

fun Fragment.parentAppBar(): AppBarLayout? {
    return (activity as MainActivity).appBar()
}