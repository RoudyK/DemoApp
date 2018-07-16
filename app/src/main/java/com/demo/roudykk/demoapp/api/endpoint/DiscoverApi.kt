package com.demo.roudykk.demoapp.api.endpoint

import com.demo.roudykk.demoapp.api.model.MoviesResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("discover/movie?sort_by=popularity.desc")
    fun getMostPopularMovies(@Query("page") page: Int): Observable<MoviesResult>

    @GET("discover/movie/?sort_by=vote_average.desc")
    fun getHighestRatedMovies(@Query("page") page: Int): Observable<MoviesResult>

    @GET("discover/movie?certification_country=US&certification.lte=G&sort_by=popularity.desc")
    fun getMostPopularForKids(@Query("page") page: Int): Observable<MoviesResult>

    @GET("discover/movie?sort_by=vote_average.desc")
    fun getMostPopularInYear(@Query("page") page: Int,
                             @Query("primary_release_year") year: Int): Observable<MoviesResult>
}