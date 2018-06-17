package com.demo.roudykk.demoapp.api.model

import com.demo.roudykk.demoapp.api.executor.ApiExecutor

data class MoviesResult (
        var executor: ApiExecutor,
        var title: String,
        var page: Int,
        var total_results: Int,
        var total_pages: Int,
        var results: ArrayList<Movie>
)