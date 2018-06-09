package com.demo.roudykk.demoapp.view.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.User
import com.demo.roudykk.demoapp.view.holder.BaseEpoxyHolder
import kotlinx.android.synthetic.main.item_user.view.*

@EpoxyModelClass(layout = R.layout.item_user)
abstract class UserModel : EpoxyModelWithHolder<BaseEpoxyHolder>() {
    @EpoxyAttribute
    lateinit var user: User

    override fun bind(holder: BaseEpoxyHolder) {
        super.bind(holder)
        holder.itemView.nameTv.text = user.name
    }
}