package com.roudykk.presentation.mapper

import com.roudykk.domain.model.ProductionCountry
import com.roudykk.presentation.mapper.ViewMapper
import com.roudykk.presentation.model.ProductionCountryView
import javax.inject.Inject

class ProductionCountryViewMapper @Inject constructor() : ViewMapper<ProductionCountryView, ProductionCountry> {

    override fun mapToView(domain: ProductionCountry): ProductionCountryView {
        return ProductionCountryView(isoName = domain.isoName,
                name = domain.name)
    }

}