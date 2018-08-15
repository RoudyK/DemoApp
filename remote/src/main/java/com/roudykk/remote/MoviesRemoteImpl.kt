package com.roudykk.remote

import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import com.roudykk.data.repository.MoviesRemote
import com.roudykk.remote.mapper.MovieGroupModelMapper
import com.roudykk.remote.mapper.MovieModelMapper
import com.roudykk.remote.mapper.PersonModelMapper
import com.roudykk.remote.model.MovieIndex
import com.roudykk.remote.model.MoviesResultModel
import com.roudykk.remote.service.DiscoverApi
import com.roudykk.remote.service.MovieApi
import com.roudykk.remote.service.PersonApi
import io.reactivex.Observable
import io.reactivex.functions.Function4
import java.time.Year
import javax.inject.Inject

class MoviesRemoteImpl @Inject constructor(
        private val discoverApi: DiscoverApi,
        private val movieApi: MovieApi,
        private val personApi: PersonApi,
        private val movieModelMapper: MovieModelMapper,
        private val personModelMapper: PersonModelMapper,
        private val movieGroupModelMapper: MovieGroupModelMapper
) : MoviesRemote {

    override fun getMovieGroups(): Observable<List<MovieGroupEntity>> {
        return Observable.zip(
                this.buildMovieGroupObservable(
                        this.discoverApi.getMostPopularForKids(0),
                        "Most Popular (Kids)",
                        MovieIndex.MOST_POPULAR_KIDS),
                this.buildMovieGroupObservable(
                        this.discoverApi.getMostPopularInYear(0, Year.now().value),
                        "Most Popular (2018)",
                        MovieIndex.MOST_POPULAR_YEAR),
                this.buildMovieGroupObservable(
                        this.discoverApi.getMostPopularMovies(0),
                        "Most Popular",
                        MovieIndex.MOST_POPULAR),
                this.buildMovieGroupObservable(
                        this.discoverApi.getHighestRatedMovies(0),
                        "Highest Rated",
                        MovieIndex.HIGHEST_RATED),
                Function4<MoviesResultModel,
                        MoviesResultModel,
                        MoviesResultModel,
                        MoviesResultModel,
                        List<MoviesResultModel>> { m1, m2, m3, m4 ->
                    listOf(m1, m2, m3, m4)
                })
                .map {
                    it.map {
                        this.movieGroupModelMapper.mapFromModel(it)
                    }
                }
    }

    private fun buildMovieGroupObservable(observable: Observable<MoviesResultModel>,
                                          title: String, index: String)
            : Observable<MoviesResultModel> {
        return observable.doOnNext { movieResult ->
            movieResult.title = title
            movieResult.index = index
        }
    }

    override fun getMovieDetails(movieId: Int): Observable<MovieEntity> {
        return this.movieApi.getMovieDetails(movieId)
                .map {
                    this.movieModelMapper.mapFromModel(it)
                }
    }

    override fun getMovies(index: String, page: Int): Observable<List<MovieEntity>> {
        val observable: Observable<MoviesResultModel> = when (index) {
            MovieIndex.HIGHEST_RATED -> this.discoverApi.getHighestRatedMovies(page)
            MovieIndex.MOST_POPULAR -> this.discoverApi.getMostPopularMovies(page)
            MovieIndex.MOST_POPULAR_YEAR -> this.discoverApi.getMostPopularInYear(page, Year.now().value)
            MovieIndex.MOST_POPULAR_KIDS -> this.discoverApi.getMostPopularForKids(page)
            else -> throw IllegalArgumentException("index must be one of the MoveIndex values")
        }

        return observable.map {
            it.results.map {
                this.movieModelMapper.mapFromModel(it)
            }
        }
    }

    override fun getPersonDetails(personId: Int): Observable<PersonEntity> {
        return this.personApi.getPersonDetails(personId)
                .map {
                    this.personModelMapper.mapFromModel(it)
                }
    }

}