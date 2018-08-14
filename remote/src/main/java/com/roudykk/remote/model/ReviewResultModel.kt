package com.roudykk.remote.model

data class ReviewResultModel(
        var page: Int = 1,
        var results: List<ReviewModel>? = null,
        var total_pages: Int = 0,
        var total_results: Int = 0)