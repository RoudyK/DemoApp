package com.demo.roudykk.demoapp.extensions

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Sets up default OverScroll behavior for [RecyclerView]
 */
fun RecyclerView.addOverScroll() {
    OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}

/**
 * Provides an easy way to build models for [EpoxyRecyclerView]
 */
fun EpoxyRecyclerView.withModels(callback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            callback()
        }
    })
}

/**
 * Provides an easy way to build models for [EpoxyRecyclerView] through DataBinding
 */
fun EpoxyRecyclerView.withModels(modelsBuilder: ModelsBuilder) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            modelsBuilder.build(this)
        }
    })
}

fun RecyclerView.withAppBar(appBarLayout: AppBarLayout) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (canScrollVertically(-1)) {
                appBarLayout.elevation = 6F
            } else {
                appBarLayout.elevation = 0F
            }
        }
    })
}