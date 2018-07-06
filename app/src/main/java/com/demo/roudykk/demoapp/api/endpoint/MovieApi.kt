package com.demo.roudykk.demoapp.api.endpoint

import com.demo.roudykk.demoapp.api.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int?): Observable<Movie>;
}