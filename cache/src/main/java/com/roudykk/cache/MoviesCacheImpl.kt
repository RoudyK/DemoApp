package com.roudykk.cache

import com.roudykk.cache.db.MoviesDatabase
import com.roudykk.cache.mapper.CacheMovieMapper
import com.roudykk.data.model.MovieEntity
import com.roudykk.data.repository.MoviesCache
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
        private val database: MoviesDatabase,
        private val mapper: CacheMovieMapper
) : MoviesCache {

    override fun addMovieWatchList(movie: MovieEntity): Completable {
        return Completable.defer {
            this.database.moviesDao().insert(this.mapper.mapToCache(movie))
            Completable.complete()
        }
    }

    override fun removeMovieWatchlist(movieId: Int): Completable {
        return Completable.defer {
            this.database.moviesDao().delete(movieId)
            Completable.complete()
        }
    }

    override fun getWatchListMovies(): Observable<List<MovieEntity>> {
        return this.database.moviesDao()
                .getMovies()
                .toObservable()
                .map {
                    it.map {
                        this.mapper.mapFromCache(it)
                    }
                }
    }

    override fun clearWatchListMovies(): Completable {
        return Completable.defer {
            this.database.moviesDao().clearMovies()
            Completable.complete()
        }
    }

}