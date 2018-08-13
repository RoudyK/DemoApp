package com.roudykk.data.repository

import com.roudykk.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface MoviesCache {

    fun addMovieWatchList(movie: MovieEntity): Completable

    fun removeMovieWatchlist(movieId: Int): Completable

    fun getWatchListMovies(): Observable<List<MovieEntity>>

    fun clearWatchListMovies(): Completable
}