package com.roudykk.domain.repository

import com.roudykk.domain.model.Movie
import com.roudykk.domain.model.MovieGroup
import com.roudykk.domain.model.Person
import io.reactivex.Completable
import io.reactivex.Observable

interface MoviesRepository {

    fun getMovieGroups(): Observable<List<MovieGroup>>

    fun getMovieDetails(movieId: Int): Observable<Movie>

    fun getMovies(index: String, page: Int): Observable<List<Movie>>

    fun addMovieWatchList(movie: Movie): Completable

    fun removeMovieWatchList(movieId: Int): Completable

    fun getWatchListMovies(): Observable<List<Movie>>

    fun getPersonDetails(personId: Int): Observable<Person>

    fun clearWatchListMovies(): Completable

    fun searchMovies(searchQuery: String): Observable<List<Movie>>
}