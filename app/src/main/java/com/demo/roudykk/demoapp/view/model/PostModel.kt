package com.demo.roudykk.demoapp.view.model

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Post
import com.demo.roudykk.demoapp.util.extensions.withModels
import com.demo.roudykk.demoapp.util.images.AppImageLoader
import com.demo.roudykk.demoapp.view.holder.BaseEpoxyHolder
import kotlinx.android.synthetic.main.item_post.view.*

@EpoxyModelClass(layout = R.layout.item_post)
abstract class PostModel : EpoxyModelWithHolder<BaseEpoxyHolder>() {
    @EpoxyAttribute
    lateinit var post: Post
    @EpoxyAttribute
    lateinit var displayDate: String

    override fun bind(holder: BaseEpoxyHolder) {
        super.bind(holder)
        holder.itemView.postUserNameTv.text = post.user.name
        holder.itemView.textTv.text = post.text
        holder.itemView.textTv.textSize = post.textSize
        holder.itemView.dateTv.text = displayDate
        holder.itemView.likeTv.text = post.likes.toString()
        holder.itemView.dislikeTv.text = post.dislikes.toString()
        holder.itemView.imagesRv.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.itemView.imagesRv.itemAnimator = null
        holder.itemView.imagesRv.withModels {
            post.imageUrls.forEach({ imageUrl ->
                PostImageModel_()
                        .id(imageUrl).imageUrl(imageUrl).addTo(this)
            })
        }
        holder.itemView.imagesRv.visibility = if (post.imageUrls.isEmpty()) {
            View.GONE
        } else{
            View.VISIBLE
        }
        AppImageLoader.loadUserImage(holder.itemView.context,
                post.user.imageUrl, holder.itemView.postUserIv)
    }
}