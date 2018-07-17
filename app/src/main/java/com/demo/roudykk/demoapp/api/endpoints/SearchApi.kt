package com.demo.roudykk.demoapp.api.endpoints

import com.demo.roudykk.demoapp.api.models.MoviesResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String): Observable<MoviesResult>
}