package com.demo.roudykk.demoapp.controllers

import android.content.Context
import com.airbnb.epoxy.Typed2EpoxyController
import com.demo.roudykk.demoapp.*
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import javax.inject.Inject

class ProfileController @Inject constructor(val context: Context) : Typed2EpoxyController<Resource<PersonView>, PersonView>() {
    var errorAction: () -> Unit = {}

    override fun buildModels(resource: Resource<PersonView>?, person: PersonView) {
        resource?.let {

            PersonAvatarBindingModel_()
                    .id("person_header")
                    .imageUrl(person.getImageUrl())
                    .addTo(this@ProfileController)

            when (it.status) {
                ResourceState.LOADING -> {
                    LoadingBindingModel_()
                            .id("loading")
                            .addTo(this)
                }
                ResourceState.ERROR -> {
                    ErrorBindingModel_()
                            .id("error")
                            .actionListener { _ -> errorAction() }
                            .addTo(this)
                }
                ResourceState.SUCCESS -> {
                    person.apply {

                        BiographyBindingModel_()
                                .id("biography")
                                .text(person.biography)
                                .addIf(!person.biography.isNullOrBlank(), this@ProfileController)

                        MetricBindingModel_()
                                .id("gender")
                                .title(context.getString(R.string.gender))
                                .value(person.getGender())
                                .addIf(person.gender != 0, this@ProfileController)

                        MetricBindingModel_()
                                .id("birthday")
                                .title(context.getString(R.string.birthday))
                                .value(person.birthday)
                                .addIf(person.birthday != null, this@ProfileController)


                        MetricBindingModel_()
                                .id("place_of_birth")
                                .title(context.getString(R.string.place_of_birth))
                                .value(person.placeOfBirth)
                                .addIf(person.placeOfBirth != null, this@ProfileController)

                        MetricBindingModel_()
                                .id("deathday")
                                .title(context.getString(R.string.date_of_death))
                                .value(person.deathday)
                                .addIf(person.deathday != null, this@ProfileController)

                        MetricBindingModel_()
                                .id("department")
                                .title(context.getString(R.string.department))
                                .value(person.knownFor)
                                .addIf(person.knownFor != null, this@ProfileController)

                        MetricBindingModel_()
                                .id("known_as")
                                .title(context.getString(R.string.known_as))
                                .value(person.knownAs?.joinToString(", "))
                                .addIf(person.knownAs != null && !person.knownAs!!.isEmpty(),
                                        this@ProfileController)
                    }
                }
            }
        }
    }
}