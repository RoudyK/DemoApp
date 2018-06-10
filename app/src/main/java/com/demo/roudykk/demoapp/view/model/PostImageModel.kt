package com.demo.roudykk.demoapp.view.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.util.images.AppImageLoader
import com.demo.roudykk.demoapp.view.holder.BaseEpoxyHolder
import kotlinx.android.synthetic.main.item_post_image.view.*

@EpoxyModelClass(layout = R.layout.item_post_image)
abstract class PostImageModel : EpoxyModelWithHolder<BaseEpoxyHolder>() {
    @EpoxyAttribute
    lateinit var imageUrl: String

    override fun bind(holder: BaseEpoxyHolder) {
        super.bind(holder)
        AppImageLoader.loadPostImage(holder.itemView.context, imageUrl, holder.itemView.postIv)
    }
}
