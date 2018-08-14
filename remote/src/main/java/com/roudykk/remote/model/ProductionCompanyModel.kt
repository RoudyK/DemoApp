package com.roudykk.remote.model

data class ProductionCompanyModel(
        var id: Int,
        var logo_path: String? = null,
        var name: String? = null,
        var origin_country: String? = null)