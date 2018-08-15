package com.roudykk.presentation.mapper

import com.roudykk.domain.model.ProductionCompany
import com.roudykk.presentation.model.ProductionCompanyView
import javax.inject.Inject

class ProductionCompanyEntityMapper @Inject constructor()
    : ViewMapper<ProductionCompanyView, ProductionCompany> {

    override fun mapFromView(view: ProductionCompanyView): ProductionCompany {
        return ProductionCompany(
                id = view.id,
                logoPath = view.logoPath,
                name = view.name,
                originCountry = view.originCountry)
    }

    override fun mapToView(domain: ProductionCompany): ProductionCompanyView {
        return ProductionCompanyView(
                id = domain.id,
                logoPath = domain.logoPath,
                name = domain.name,
                originCountry = domain.originCountry)
    }

}