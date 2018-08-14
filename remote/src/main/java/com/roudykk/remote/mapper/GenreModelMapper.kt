package com.roudykk.remote.mapper

import com.roudykk.data.model.GenreEntity
import com.roudykk.remote.model.GenreModel
import javax.inject.Inject

class GenreModelMapper @Inject constructor() : ModelMapper<GenreModel, GenreEntity> {

    override fun mapFromModel(model: GenreModel): GenreEntity {
        return GenreEntity(id = model.id,
                name = model.name)
    }

    override fun mapToModel(entity: GenreEntity): GenreModel{
        return GenreModel(id = entity.id,
                name = entity.name)
    }

}