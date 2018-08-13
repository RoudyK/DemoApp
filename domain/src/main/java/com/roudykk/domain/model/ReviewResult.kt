package com.roudykk.domain.model

data class ReviewResult(
        var page: Int,
        var results: ArrayList<Review>? = null,
        var total_pages: Int,
        var total_results: Int)