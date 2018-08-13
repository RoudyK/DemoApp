package com.roudykk.data

import com.roudykk.data.mapper.MovieEntityMapper
import com.roudykk.data.mapper.MovieGroupEntityMapper
import com.roudykk.data.mapper.PersonEntityMapper
import com.roudykk.data.store.MoviesDataStoreFactory
import com.roudykk.domain.model.Movie
import com.roudykk.domain.model.MovieGroup
import com.roudykk.domain.model.Person
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
        private val movieGroupEntityMapper: MovieGroupEntityMapper,
        private val movieEntityMapper: MovieEntityMapper,
        private val personEntityMapper: PersonEntityMapper,
        private val factory: MoviesDataStoreFactory
) : MoviesRepository {

    override fun getMovieGroups(): Observable<List<MovieGroup>> {
        return this.factory.getRemoteDataStore().getMovieGroups()
                .map {
                    it.map {
                        this.movieGroupEntityMapper.mapFromEntity(it)
                    }
                }
    }

    override fun getMovieDetails(movieId: Int): Observable<Movie> {
        return this.factory.getRemoteDataStore().getMovieDetails(movieId)
                .map {
                    this.movieEntityMapper.mapFromEntity(it)
                }
    }

    override fun getMovies(index: String, page: Int): Observable<List<Movie>> {
        return this.factory.getRemoteDataStore().getMovies(index, page)
                .map {
                    it.map {
                        this.movieEntityMapper.mapFromEntity(it)
                    }
                }
    }

    override fun addMovieWatchList(movie: Movie): Completable {
        return this.factory.getCacheDataStore().addMovieWatchList(
                this.movieEntityMapper.mapToEntity(movie)
        )
    }

    override fun removeMovieWatchList(movieId: Int): Completable {
        return this.factory.getCacheDataStore().removeMovieWatchlist(movieId)
    }

    override fun getWatchListMovies(): Observable<List<Movie>> {
        return this.factory.getCacheDataStore().getWatchListMovies()
                .map {
                    it.map {
                        this.movieEntityMapper.mapFromEntity(it)
                    }
                }
    }

    override fun getPersonDetails(personId: Int): Observable<Person> {
        return this.factory.getRemoteDataStore().getPersonDetails(personId)
                .map {
                    this.personEntityMapper.mapFromEntity(it)
                }
    }

}