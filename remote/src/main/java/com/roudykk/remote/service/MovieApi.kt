package com.roudykk.remote.service

import com.roudykk.remote.model.MovieModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movie_id}?append_to_response=videos,reviews,credits")
    fun getMovieDetails(@Path("movie_id") movieId: Int?): Observable<MovieModel>
}