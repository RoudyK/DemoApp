package com.roudykk.remote.mapper

import com.roudykk.data.model.ProductionCountryEntity
import com.roudykk.remote.model.ProductionCountryModel
import javax.inject.Inject

class ProductionCountryModelMapper @Inject constructor()
    : ModelMapper<ProductionCountryModel, ProductionCountryEntity> {

    override fun mapToModel(entity: ProductionCountryEntity): ProductionCountryModel {
        return ProductionCountryModel(iso_3116_1 = entity.isoName,
                name = entity.name)
    }

    override fun mapFromModel(model: ProductionCountryModel): ProductionCountryEntity {
        return ProductionCountryEntity(isoName = model.iso_3116_1,
                name = model.name)
    }

}