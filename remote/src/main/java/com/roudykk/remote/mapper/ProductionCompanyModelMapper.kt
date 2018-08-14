package com.roudykk.remote.mapper

import com.roudykk.data.model.ProductionCompanyEntity
import com.roudykk.remote.model.ProductionCompanyModel
import javax.inject.Inject

class ProductionCompanyModelMapper @Inject constructor()
    : ModelMapper<ProductionCompanyModel, ProductionCompanyEntity> {

    override fun mapToModel(entity: ProductionCompanyEntity): ProductionCompanyModel {
        return ProductionCompanyModel(
                id = entity.id,
                logo_path = entity.logoPath,
                name = entity.name,
                origin_country = entity.originCountry)
    }

    override fun mapFromModel(model: ProductionCompanyModel): ProductionCompanyEntity {
        return ProductionCompanyEntity(
                id = model.id,
                logoPath = model.logo_path,
                name = model.name,
                originCountry = model.origin_country)
    }

}