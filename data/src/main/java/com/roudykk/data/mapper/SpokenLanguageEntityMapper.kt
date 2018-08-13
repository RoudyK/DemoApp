package com.roudykk.data.mapper

import com.roudykk.data.model.SpokenLanguageEntity
import com.roudykk.domain.model.SpokenLanguage
import javax.inject.Inject

class SpokenLanguageEntityMapper @Inject constructor() : Mapper<SpokenLanguageEntity, SpokenLanguage> {

    override fun mapFromEntity(entity: SpokenLanguageEntity): SpokenLanguage {
        return SpokenLanguage(name = entity.name,
                isoName = entity.isoName)
    }

    override fun mapToEntity(domain: SpokenLanguage): SpokenLanguageEntity {
        return SpokenLanguageEntity(name = domain.name,
                isoName = domain.isoName)
    }

}