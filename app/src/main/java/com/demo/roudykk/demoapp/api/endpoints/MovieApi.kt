package com.demo.roudykk.demoapp.api.endpoints

import com.demo.roudykk.demoapp.api.models.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movie_id}?append_to_response=videos,reviews,credits")
    fun getMovieDetails(@Path("movie_id") movieId: Int?): Observable<Movie>
}