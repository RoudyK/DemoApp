package com.roudykk.data.store

import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import com.roudykk.data.repository.MoviesDataStore
import com.roudykk.data.repository.MoviesRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(
        private val moviesRemote: MoviesRemote
) : MoviesDataStore {

    override fun getMovieGroups(): Observable<List<MovieGroupEntity>> {
        return this.moviesRemote.getMovieGroups()
    }

    override fun getMovieDetails(movieId: Int): Observable<MovieEntity> {
        return this.moviesRemote.getMovieDetails(movieId)
    }

    override fun getMovies(index: String, page: Int): Observable<List<MovieEntity>> {
        return this.moviesRemote.getMovies(index, page)
    }

    override fun getPersonDetails(personId: Int): Observable<PersonEntity> {
        return this.moviesRemote.getPersonDetails(personId)
    }

    override fun addMovieWatchList(movie: MovieEntity): Completable {
        throw UnsupportedOperationException("Add Movie WatchList not supported here")
    }

    override fun removeMovieWatchlist(movieId: Int): Completable {
        throw UnsupportedOperationException("Remove Movie WatchList not supported here")
    }

    override fun getWatchListMovies(): Observable<List<MovieEntity>> {
        throw UnsupportedOperationException("Get WatchList Movies not supported here")
    }

    override fun clearWatchListMovies(): Completable {
        throw UnsupportedOperationException("Clear WatchList Movies not supported here")
    }

}