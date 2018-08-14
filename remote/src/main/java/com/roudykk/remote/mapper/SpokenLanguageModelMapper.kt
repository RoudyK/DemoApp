package com.roudykk.remote.mapper

import com.roudykk.data.model.SpokenLanguageEntity
import com.roudykk.remote.model.SpokenLanguageModel
import javax.inject.Inject

class SpokenLanguageModelMapper @Inject constructor()
    : ModelMapper<SpokenLanguageModel, SpokenLanguageEntity> {

    override fun mapToModel(entity: SpokenLanguageEntity): SpokenLanguageModel {
        return SpokenLanguageModel(name = entity.name,
                iso_639_1 = entity.isoName)
    }

    override fun mapFromModel(model: SpokenLanguageModel): SpokenLanguageEntity {
        return SpokenLanguageEntity(name = model.name,
                isoName = model.iso_639_1)
    }

}