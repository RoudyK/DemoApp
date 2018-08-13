package com.roudykk.domain.model

data class ProductionCompany(
        var id: Int,
        var logo_path: String? = null,
        var name: String? = null,
        var origin_country: String? = null)