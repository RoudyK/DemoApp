package com.demo.roudykk.demoapp.view.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.api.model.Post
import com.demo.roudykk.demoapp.view.model.PostModel_

class PostsController : TypedEpoxyController<List<Post>>() {

    override fun buildModels(posts: List<Post>?) {
        posts?.forEach({ post -> PostModel_().id(post.id).post(post).addTo(this) })
    }

}