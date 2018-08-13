package com.roudykk.data.store

import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import com.roudykk.data.repository.MoviesCache
import com.roudykk.data.repository.MoviesDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesCacheDataStore @Inject constructor(
        private val moviesCache: MoviesCache
) : MoviesDataStore {

    override fun getMovieGroups(): Observable<List<MovieGroupEntity>> {
        throw UnsupportedOperationException("Get Movies Groups not supported here")
    }

    override fun getMovieDetails(movieId: Int): Observable<MovieEntity> {
        throw UnsupportedOperationException("Get Movie Details not supported here")
    }

    override fun getMovies(index: String, page: Int): Observable<List<MovieEntity>> {
        throw UnsupportedOperationException("Get Movies not supported here")
    }

    override fun getPersonDetails(personId: Int): Observable<PersonEntity> {
        throw UnsupportedOperationException("Get Person Details not supported here")
    }

    override fun addMovieWatchList(movie: MovieEntity): Completable {
        return this.moviesCache.addMovieWatchList(movie)
    }

    override fun removeMovieWatchlist(movieId: Int): Completable {
        return this.moviesCache.removeMovieWatchlist(movieId)
    }

    override fun getWatchListMovies(): Observable<List<MovieEntity>> {
        return this.moviesCache.getWatchListMovies()
    }

    override fun clearWatchListMovies(): Completable {
        return this.moviesCache.clearWatchListMovies()
    }

}