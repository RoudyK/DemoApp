package com.roudykk.data.model

data class ProductionCompanyEntity(
        var id: Int,
        var logoPath: String? = null,
        var name: String? = null,
        var originCountry: String? = null)