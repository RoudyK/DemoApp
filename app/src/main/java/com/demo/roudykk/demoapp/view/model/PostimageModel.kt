package com.demo.roudykk.demoapp.view.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.util.GlideApp
import com.demo.roudykk.demoapp.view.holder.BaseEpoxyHolder
import kotlinx.android.synthetic.main.item_post_image.view.*

@EpoxyModelClass(layout = R.layout.item_post_image)
abstract class PostImageModel : EpoxyModelWithHolder<BaseEpoxyHolder>() {
    @EpoxyAttribute
    lateinit var imageUrl: String

    override fun bind(holder: BaseEpoxyHolder) {
        super.bind(holder)

        GlideApp.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.background_post_image)
                .transforms(CenterCrop(), RoundedCorners(10))
                .into(holder.itemView.postIv)
    }
}
