package com.roudykk.remote.model

data class MoviesResultModel(
        var certificationCountry: String? = null,
        var certification: String? = null,
        var title: String,
        var index: String,
        var page: Int = 1,
        var total_results: Int = 0,
        var total_pages: Int = 1,
        var results: List<MovieModel>)