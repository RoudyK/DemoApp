package com.roudykk.remote.mapper

import com.roudykk.data.model.PersonEntity
import com.roudykk.remote.model.PersonModel
import javax.inject.Inject

open class PersonModelMapper @Inject constructor() : ModelMapper<PersonModel, PersonEntity> {

    override fun mapFromModel(model: PersonModel): PersonEntity {
        return PersonEntity(id = model.id,
                name = model.name,
                castId = model.cast_id,
                creditId = model.credit_id,
                profilePath = model.profile_path,
                character = model.character,
                gender = model.gender,
                order = model.order,
                birthday = model.birthday,
                deathday = model.deathday,
                placeOfBirth = model.place_of_birth,
                biography = model.biography,
                adult = model.adult,
                knownAs = model.also_known_as,
                knownFor = model.known_for_department,
                popularity = model.popularity)
    }

    override fun mapToModel(entity: PersonEntity): PersonModel {
        return PersonModel(id = entity.id,
                name = entity.name,
                cast_id = entity.castId,
                credit_id = entity.creditId,
                profile_path = entity.profilePath,
                character = entity.character,
                gender = entity.gender,
                order = entity.order,
                birthday = entity.birthday,
                deathday = entity.deathday,
                place_of_birth = entity.placeOfBirth,
                biography = entity.biography,
                adult = entity.adult,
                also_known_as = entity.knownAs,
                known_for_department = entity.knownFor,
                popularity = entity.popularity)
    }

}