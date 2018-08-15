package com.roudykk.data.repository

import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface MoviesDataStore {
    fun getMovieGroups(): Observable<List<MovieGroupEntity>>

    fun getMovieDetails(movieId: Int): Observable<MovieEntity>

    fun getMovies(index: String, page: Int): Observable<List<MovieEntity>>

    fun getPersonDetails(personId: Int): Observable<PersonEntity>

    fun addMovieWatchList(movie: MovieEntity): Completable

    fun removeMovieWatchlist(movieId: Int): Completable

    fun getWatchListMovies(): Observable<List<MovieEntity>>

    fun clearWatchListMovies(): Completable

    fun searchMovies(searchQuery: String): Observable<List<MovieEntity>>
}