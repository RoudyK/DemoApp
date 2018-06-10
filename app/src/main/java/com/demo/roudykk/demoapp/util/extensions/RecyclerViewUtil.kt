package com.demo.roudykk.demoapp.util.extensions

import android.support.v7.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

fun RecyclerView.addOverScroll() {
    OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}

fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            buildModelsCallback()
        }
    })
}