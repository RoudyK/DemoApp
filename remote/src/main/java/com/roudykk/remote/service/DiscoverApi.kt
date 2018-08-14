package com.roudykk.remote.service

import com.roudykk.remote.model.MoviesResultModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("discover/movie?sort_by=popularity.desc")
    fun getMostPopularMovies(@Query("page") page: Int): Observable<MoviesResultModel>

    @GET("discover/movie/?sort_by=vote_average.desc")
    fun getHighestRatedMovies(@Query("page") page: Int): Observable<MoviesResultModel>

    @GET("discover/movie?=US&certification.lte=G&sort_by=popularity.desc")
    fun getMostPopularForKids(@Query("page") page: Int): Observable<MoviesResultModel>

    @GET("discover/movie?sort_by=vote_average.desc")
    fun getMostPopularInYear(@Query("page") page: Int,
                             @Query("primary_release_year") year: Int): Observable<MoviesResultModel>
}