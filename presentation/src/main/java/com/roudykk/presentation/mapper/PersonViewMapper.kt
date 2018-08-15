package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Person
import com.roudykk.presentation.model.PersonView
import javax.inject.Inject

open class PersonViewMapper @Inject constructor() : ViewMapper<PersonView, Person> {

    override fun mapFromView(view: PersonView): Person {
        return Person(id = view.id,
                name = view.name,
                castId = view.castId,
                creditId = view.creditId,
                profilePath = view.profilePath,
                character = view.character,
                gender = view.gender,
                order = view.order,
                birthday = view.birthday,
                deathday = view.deathday,
                placeOfBirth = view.placeOfBirth,
                biography = view.biography,
                adult = view.adult,
                knownAs = view.knownAs,
                knownFor = view.knownFor,
                popularity = view.popularity)
    }

    override fun mapToView(domain: Person): PersonView {
        return PersonView(id = domain.id,
                name = domain.name,
                castId = domain.castId,
                creditId = domain.creditId,
                profilePath = domain.profilePath,
                character = domain.character,
                gender = domain.gender,
                order = domain.order,
                birthday = domain.birthday,
                deathday = domain.deathday,
                placeOfBirth = domain.placeOfBirth,
                biography = domain.biography,
                adult = domain.adult,
                knownAs = domain.knownAs,
                knownFor = domain.knownFor,
                popularity = domain.popularity)
    }

}