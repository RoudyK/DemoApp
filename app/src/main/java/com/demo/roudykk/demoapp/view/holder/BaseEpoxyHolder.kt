package com.demo.roudykk.demoapp.view.holder

import android.support.annotation.CallSuper
import android.view.View
import com.airbnb.epoxy.EpoxyHolder

/*
Used as a base holder to cut down redundant calls or var creation
to get the itemView
 */
class BaseEpoxyHolder : EpoxyHolder() {
    lateinit var itemView: View

    @CallSuper
    override fun bindView(itemView: View) {
        this.itemView = itemView
    }
}