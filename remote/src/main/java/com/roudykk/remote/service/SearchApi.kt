package com.roudykk.remote.service

import com.roudykk.remote.model.MoviesResultModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String): Observable<MoviesResultModel>
}