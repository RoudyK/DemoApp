package com.roudykk.data.mapper

import com.roudykk.data.model.PersonEntity
import com.roudykk.domain.model.Person
import javax.inject.Inject

open class PersonEntityMapper @Inject constructor() : Mapper<PersonEntity, Person> {

    override fun mapFromEntity(entity: PersonEntity): Person {
        return Person(id = entity.id,
                name = entity.name,
                castId = entity.castId,
                creditId = entity.creditId,
                profilePath = entity.profilePath,
                character = entity.character,
                gender = entity.gender,
                order = entity.order,
                birthday = entity.birthday,
                deathday = entity.deathday,
                placeOfBirth = entity.placeOfBirth,
                biography = entity.biography,
                adult = entity.adult,
                knownAs = entity.knownAs,
                knownFor = entity.knownFor,
                popularity = entity.popularity)
    }

    override fun mapToEntity(domain: Person): PersonEntity {
        return PersonEntity(id = domain.id,
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