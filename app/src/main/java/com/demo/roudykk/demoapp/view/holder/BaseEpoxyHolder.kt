package com.demo.roudykk.demoapp.view.holder

import android.support.annotation.CallSuper
import android.view.View
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyHolder

class BaseEpoxyHolder : EpoxyHolder() {
    lateinit var itemView: View

    @CallSuper
    override fun bindView(itemView: View) {
        ButterKnife.bind(this, itemView)
        this.itemView = itemView
    }
}