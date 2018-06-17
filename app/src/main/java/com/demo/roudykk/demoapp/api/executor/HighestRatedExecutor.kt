package com.demo.roudykk.demoapp.api.executor

import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.model.MoviesResult
import io.reactivex.Observable

class HighestRatedExecutor : ApiExecutor {
    override fun title(): String {
        return "Highest Rated"
    }

    override fun getMovies(page: Int): Observable<MoviesResult> {
        return Api.discoverApi().getHighestRatedMovies(page)
    }

}