package com.roudykk.presentation.model

data class ProductionCompanyView(
        var id: Int,
        var logoPath: String? = null,
        var name: String? = null,
        var originCountry: String? = null)