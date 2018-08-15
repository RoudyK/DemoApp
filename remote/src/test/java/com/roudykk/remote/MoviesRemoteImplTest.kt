package com.roudykk.remote

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.remote.mapper.MovieGroupModelMapper
import com.roudykk.remote.mapper.MovieModelMapper
import com.roudykk.remote.mapper.PersonModelMapper
import com.roudykk.remote.model.MovieIndex
import com.roudykk.remote.model.MovieModel
import com.roudykk.remote.model.MoviesResultModel
import com.roudykk.remote.service.DiscoverApi
import com.roudykk.remote.service.MovieApi
import com.roudykk.remote.service.PersonApi
import com.roudykk.remote.service.SearchApi
import com.roudykk.remote.test.MoviesRemoteFactory
import io.reactivex.Observable
import org.junit.Test
import java.time.Year

class MoviesRemoteImplTest {

    private val discoverApi = mock<DiscoverApi>()
    private val movieApi = mock<MovieApi>()
    private val personApi = mock<PersonApi>()
    private val searchApi = mock<SearchApi>()

    private val movieModelMapper = mock<MovieModelMapper>()
    private val personModelMapper = mock<PersonModelMapper>()
    private val movieGroupModelMapper = mock<MovieGroupModelMapper>()

    private val remote = MoviesRemoteImpl(this.discoverApi, this.movieApi,
            this.personApi, this.searchApi, this.movieModelMapper,
            this.personModelMapper, this.movieGroupModelMapper)

    @Test
    fun getMovieGroupsCompletes() {
        this.stubGetMovieGroups(Observable.just(MoviesRemoteFactory.makeMoviesResultModel()))
        this.remote.getMovieGroups().test().assertComplete()
    }

    @Test
    fun getMovieGroupsReturnsData() {
        val movieResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        this.stubGetMovieGroups(Observable.just(movieResultModel))
        this.stubMovieGroupMapper(movieResultModel, movieGroupEntity)

        val observer = this.remote.getMovieGroups().test()
        observer.assertValue(listOf(movieGroupEntity, movieGroupEntity,
                movieGroupEntity, movieGroupEntity))
    }

    @Test
    fun getMovieGroupsCallsServer() {
        val movieResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        this.stubGetMovieGroups(Observable.just(movieResultModel))
        this.stubMovieGroupMapper(movieResultModel, movieGroupEntity)

        this.remote.getMovieGroups().test()

        verify(this.discoverApi).getMostPopularMovies(any())
        verify(this.discoverApi).getMostPopularForKids(any())
        verify(this.discoverApi).getMostPopularInYear(any(), any())
        verify(this.discoverApi).getHighestRatedMovies(any())
    }

    @Test
    fun getMovieGroupsCallsServerCorrectParams() {
        val movieResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        this.stubGetMovieGroups(Observable.just(movieResultModel))
        this.stubMovieGroupMapper(movieResultModel, movieGroupEntity)

        this.remote.getMovieGroups().test()

        verify(this.discoverApi).getMostPopularMovies(1)
        verify(this.discoverApi).getMostPopularForKids(1)
        verify(this.discoverApi).getMostPopularInYear(1, Year.now().value)
        verify(this.discoverApi).getHighestRatedMovies(1)
    }

    @Test
    fun getMovieDetailsCompletes() {
        val movieModel = MoviesRemoteFactory.makeMovieModel()
        val movieEntity = MoviesRemoteFactory.makeMovieEntity()

        this.stubMovieModelMapper(movieModel, movieEntity)
        this.stubGetMovieDetails(Observable.just(movieModel))


        this.remote.getMovieDetails(MoviesRemoteFactory.randomInt()).test().assertComplete()
    }

    @Test
    fun getMovieDetailsReturnsData() {
        val movieModel = MoviesRemoteFactory.makeMovieModel()
        val movieEntity = MoviesRemoteFactory.makeMovieEntity()

        this.stubMovieModelMapper(movieModel, movieEntity)
        this.stubGetMovieDetails(Observable.just(movieModel))


        this.remote.getMovieDetails(MoviesRemoteFactory.randomInt()).test()
                .assertValue(movieEntity)
    }

    @Test
    fun getMovieDetailsCallsServer() {
        val movieModel = MoviesRemoteFactory.makeMovieModel()
        val movieEntity = MoviesRemoteFactory.makeMovieEntity()

        this.stubMovieModelMapper(movieModel, movieEntity)
        this.stubGetMovieDetails(Observable.just(movieModel))

        this.remote.getMovieDetails(MoviesRemoteFactory.randomInt()).test()

        verify(this.movieApi).getMovieDetails(any())
    }

    @Test
    fun getMovieDetailsCallsServerCorrectParams() {
        val movieModel = MoviesRemoteFactory.makeMovieModel()
        val movieEntity = MoviesRemoteFactory.makeMovieEntity()
        val movieId = MoviesRemoteFactory.randomInt()

        this.stubMovieModelMapper(movieModel, movieEntity)
        this.stubGetMovieDetails(Observable.just(movieModel))

        this.remote.getMovieDetails(movieId).test()

        verify(this.movieApi).getMovieDetails(movieId)
    }

    @Test
    fun getMoviesCompletes() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        this.stubMovieGroupMapper(moviesResultModel, movieGroupEntity)
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.HIGHEST_RATED, MoviesRemoteFactory.randomInt())
                .test().assertComplete()
    }

    @Test
    fun getMoviesReturnsDataHighestRated() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.HIGHEST_RATED, MoviesRemoteFactory.randomInt())
                .test().assertValue(movieGroupEntity.movies)
    }

    @Test
    fun getMoviesReturnsDataMostPopular() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR, MoviesRemoteFactory.randomInt())
                .test().assertValue(movieGroupEntity.movies)
    }

    @Test
    fun getMoviesReturnsDataMostPopularKids() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR_KIDS, MoviesRemoteFactory.randomInt())
                .test().assertValue(movieGroupEntity.movies)
    }


    @Test
    fun getMoviesReturnsDataMostPopularYear() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR_YEAR, MoviesRemoteFactory.randomInt())
                .test().assertValue(movieGroupEntity.movies)
    }


    @Test(expected = IllegalArgumentException::class)
    fun getMoviesThrowsException() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MoviesRemoteFactory.randomString(), MoviesRemoteFactory.randomInt())
                .test().assertValue(movieGroupEntity.movies)
    }


    @Test
    fun getMoviesCallsServerMostPopular() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val page = MoviesRemoteFactory.randomInt()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR, page).test()

        verify(this.discoverApi).getMostPopularMovies(page)
    }

    @Test
    fun getMoviesCallsServerMostPopularYear() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val page = MoviesRemoteFactory.randomInt()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR_YEAR, page).test()

        verify(this.discoverApi).getMostPopularInYear(page, Year.now().value)
    }

    @Test
    fun getMoviesCallsServerHighestRated() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val page = MoviesRemoteFactory.randomInt()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.HIGHEST_RATED, page).test()

        verify(this.discoverApi).getHighestRatedMovies(page)
    }

    @Test
    fun getMoviesCallsServerMostPopularKids() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val page = MoviesRemoteFactory.randomInt()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }
        this.stubGetMovieGroups(Observable.just(moviesResultModel))

        this.remote.getMovies(MovieIndex.MOST_POPULAR_KIDS, page).test()

        verify(this.discoverApi).getMostPopularForKids(page)
    }

    @Test
    fun searchMoviesCompletes() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val searchQuery = MoviesRemoteFactory.randomString()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }

        this.stubSearchMovies(Observable.just(moviesResultModel))

        val observer = this.remote.searchMovies(searchQuery).test()
        observer.assertComplete()
    }

    @Test
    fun searchMoviesReturnsData() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val searchQuery = MoviesRemoteFactory.randomString()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }

        this.stubSearchMovies(Observable.just(moviesResultModel))

        val observer = this.remote.searchMovies(searchQuery).test()
        observer.assertValue(movieGroupEntity.movies)
    }


    @Test
    fun searchMoviesCallsServer() {
        val moviesResultModel = MoviesRemoteFactory.makeMoviesResultModel()
        val movieGroupEntity = MoviesRemoteFactory.makeMovieGroupEntity()
        val searchQuery = MoviesRemoteFactory.randomString()

        moviesResultModel.results.forEachIndexed { index, _ ->
            this.stubMovieModelMapper(moviesResultModel.results[index], movieGroupEntity.movies[index])
        }

        this.stubSearchMovies(Observable.just(moviesResultModel))

        this.remote.searchMovies(searchQuery).test()
        verify(this.searchApi).searchMovie(searchQuery)
    }

    private fun stubMovieGroupMapper(moviesResultModel: MoviesResultModel,
                                     movieGroupEntity: MovieGroupEntity) {
        whenever(this.movieGroupModelMapper.mapFromModel(moviesResultModel))
                .thenReturn(movieGroupEntity)
    }

    private fun stubMovieModelMapper(movieModel: MovieModel, movieEntity: MovieEntity) {
        whenever(this.movieModelMapper.mapFromModel(movieModel))
                .thenReturn(movieEntity)
    }

    private fun stubGetMovieDetails(observable: Observable<MovieModel>) {
        whenever(this.movieApi.getMovieDetails(any()))
                .thenReturn(observable)
    }

    private fun stubGetMovieGroups(observable: Observable<MoviesResultModel>) {
        whenever(this.discoverApi.getMostPopularForKids(any()))
                .thenReturn(observable)

        whenever(this.discoverApi.getMostPopularInYear(any(), any()))
                .thenReturn(observable)

        whenever(this.discoverApi.getMostPopularMovies(any()))
                .thenReturn(observable)

        whenever(this.discoverApi.getHighestRatedMovies(any()))
                .thenReturn(observable)
    }

    private fun stubSearchMovies(observable: Observable<MoviesResultModel>) {
        whenever(this.searchApi.searchMovie(any()))
                .thenReturn(observable)
    }
}