package com.demo.roudykk.demoapp.view.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.api.model.User
import com.demo.roudykk.demoapp.view.model.UserModel_

class UsersController : TypedEpoxyController<List<User>>() {

    override fun buildModels(users: List<User>?) {
        users?.forEach { user -> UserModel_().id(user.id).user(user).addTo(this) }
    }

}