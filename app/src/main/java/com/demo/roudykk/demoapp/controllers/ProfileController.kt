package com.demo.roudykk.demoapp.controllers

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.BiographyBindingModel_
import com.demo.roudykk.demoapp.MetricBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.models.Person

class ProfileController(private val context: Context) : TypedEpoxyController<Person>() {

    override fun buildModels(person: Person?) {
        person?.apply {

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
                    .value(person.place_of_birth)
                    .addIf(person.place_of_birth != null, this@ProfileController)

            MetricBindingModel_()
                    .id("deathday")
                    .title(context.getString(R.string.date_of_death))
                    .value(person.deathday)
                    .addIf(person.deathday != null, this@ProfileController)

            MetricBindingModel_()
                    .id("department")
                    .title(context.getString(R.string.department))
                    .value(person.known_for_department)
                    .addIf(person.known_for_department != null, this@ProfileController)
            
            MetricBindingModel_()
                    .id("known_as")
                    .title(context.getString(R.string.known_as))
                    .value(person.also_known_as.joinToString(", "))
                    .addIf(!person.also_known_as.isEmpty(), this@ProfileController)


        }
    }

}