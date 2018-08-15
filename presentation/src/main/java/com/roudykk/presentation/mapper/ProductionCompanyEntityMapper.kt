package com.roudykk.presentation.mapper

import com.roudykk.domain.model.ProductionCompany
import com.roudykk.presentation.model.ProductionCompanyView
import javax.inject.Inject

class ProductionCompanyEntityMapper @Inject constructor()
    : ViewMapper<ProductionCompanyView, ProductionCompany> {

    override fun mapToView(domain: ProductionCompany): ProductionCompanyView {
        return ProductionCompanyView(
                id = domain.id,
                logoPath = domain.logoPath,
                name = domain.name,
                originCountry = domain.originCountry)
    }

}