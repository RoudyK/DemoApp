package com.roudykk.data.mapper

import com.roudykk.data.model.ProductionCompanyEntity
import com.roudykk.domain.model.ProductionCompany
import javax.inject.Inject

class ProductionCompanyEntityMapper @Inject constructor() : Mapper<ProductionCompanyEntity, ProductionCompany> {

    override fun mapFromEntity(entity: ProductionCompanyEntity): ProductionCompany {
        return ProductionCompany(
                id = entity.id,
                logoPath = entity.logoPath,
                name = entity.name,
                originCountry = entity.originCountry)
    }

    override fun mapToEntity(domain: ProductionCompany): ProductionCompanyEntity {
        return ProductionCompanyEntity(
                id = domain.id,
                logoPath = domain.logoPath,
                name = domain.name,
                originCountry = domain.originCountry)
    }

}