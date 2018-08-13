package com.roudykk.data.mapper

import com.roudykk.data.model.ProductionCountryEntity
import com.roudykk.domain.model.ProductionCountry
import javax.inject.Inject

class ProductionCountryEntityMapper @Inject constructor() : Mapper<ProductionCountryEntity, ProductionCountry> {

    override fun mapFromEntity(entity: ProductionCountryEntity): ProductionCountry {
        return ProductionCountry(isoName = entity.isoName,
                name = entity.name)
    }

    override fun mapToEntity(domain: ProductionCountry): ProductionCountryEntity {
        return ProductionCountryEntity(isoName = domain.isoName,
                name = domain.name)
    }

}