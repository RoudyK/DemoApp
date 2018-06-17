package com.demo.roudykk.demoapp.api.executor

import com.demo.roudykk.demoapp.api.model.MoviesResult
import io.reactivex.Observable
import java.io.Serializable

interface ApiExecutor: Serializable {

    fun title(): String

    fun getMovies(page: Int): Observable<MoviesResult>
}