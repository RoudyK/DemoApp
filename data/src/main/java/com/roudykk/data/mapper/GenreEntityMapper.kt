package com.roudykk.data.mapper

import com.roudykk.data.model.GenreEntity
import com.roudykk.domain.model.Genre
import javax.inject.Inject

class GenreEntityMapper @Inject constructor() : Mapper<GenreEntity, Genre> {

    override fun mapFromEntity(entity: GenreEntity): Genre {
        return Genre(id = entity.id,
                name = entity.name)
    }

    override fun mapToEntity(domain: Genre): GenreEntity {
        return GenreEntity(id = domain.id,
                name = domain.name)
    }

}