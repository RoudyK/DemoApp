package com.roudykk.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.data.mapper.MovieEntityMapper
import com.roudykk.data.mapper.MovieGroupEntityMapper
import com.roudykk.data.mapper.PersonEntityMapper
import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import com.roudykk.data.repository.MoviesDataStore
import com.roudykk.data.store.MoviesDataStoreFactory
import com.roudykk.data.test.MoviesEntityFactory
import com.roudykk.domain.model.Movie
import com.roudykk.domain.model.MovieGroup
import com.roudykk.domain.model.Person
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class MoviesDataStoreRepositoryTest {

    private val movieEntityMapper = mock<MovieEntityMapper>()
    private val movieGroupEntityMapper = mock<MovieGroupEntityMapper>()
    private val personEntityMapper = mock<PersonEntityMapper>()
    private val factory = mock<MoviesDataStoreFactory>()
    private val store = mock<MoviesDataStore>()

    private val moviesDataRepository = MoviesDataRepository(
            this.movieGroupEntityMapper,
            this.movieEntityMapper,
            this.personEntityMapper,
            this.factory)

    @Before
    fun setup() {
        this.stubCacheDataStore()
        this.stubRemoteDataStore()
    }

    @Test
    fun getMovieGroupsCompletes() {
        val movieGroups = MoviesEntityFactory.makeMovieGroupsEntity()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        val observer = this.moviesDataRepository.getMovieGroups().test()

        observer.assertComplete()
    }

    @Test
    fun getMovieGroupsReturnsData() {
        val movieGroupEntity = MoviesEntityFactory.makeMovieGroupEntity()
        val movieGroup = MoviesEntityFactory.makeMovieGroup()
        this.stubGetMovieGroups(Observable.just(listOf(movieGroupEntity)))
        this.stubMovieGroupMapper(movieGroupEntity, movieGroup)

        val observer = this.moviesDataRepository.getMovieGroups().test()

        observer.assertValue(listOf(movieGroup))
    }

    @Test
    fun getMovieDetailsCompletes() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movieEntity))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getMovieDetails(MoviesEntityFactory.randomInt())
                .test()

        observer.assertComplete()
    }

    @Test
    fun getMovieDetailsReturnsData() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movieEntity))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getMovieDetails(MoviesEntityFactory.randomInt())
                .test()

        observer.assertValue(movie)
    }

    @Test
    fun getMoviesCompletes() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetMovies(Observable.just(listOf(movieEntity)))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getMovies(MoviesEntityFactory.randomString(),
                MoviesEntityFactory.randomInt()).test()

        observer.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetMovies(Observable.just(listOf(movieEntity)))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getMovies(MoviesEntityFactory.randomString(),
                MoviesEntityFactory.randomInt()).test()

        observer.assertValue(listOf(movie))
    }

    @Test
    fun addMovieWatchListCompletes() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubAddMovieWatchList(Completable.complete())
        this.stubMovieToMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.addMovieWatchList(movie).test()

        observer.assertComplete()
    }

    @Test
    fun removeMovieWatchListCompletes() {
        this.stubRemoveMovieWatchList(Completable.complete())

        val observer = this.moviesDataRepository.removeMovieWatchList(MoviesEntityFactory
                .randomInt()).test()

        observer.assertComplete()
    }

    @Test
    fun getWatchListMoviesCompletes() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetWatchListMovie(Observable.just(listOf(movieEntity)))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getWatchListMovies().test()

        observer.assertComplete()
    }

    @Test
    fun getWatchListMoviesReturnsData() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = MoviesEntityFactory.makeMovie()
        this.stubGetWatchListMovie(Observable.just(listOf(movieEntity)))
        this.stubMovieMapper(movieEntity, movie)

        val observer = this.moviesDataRepository.getWatchListMovies().test()

        observer.assertValue(listOf(movie))
    }

    @Test
    fun getPersonDetailsCompletes() {
        val personEntity = MoviesEntityFactory.makePersonEntity()
        val person = MoviesEntityFactory.makePerson()
        this.stubGetPersonDetails(Observable.just(personEntity))
        this.stubPersonMapper(personEntity, person)

        val observer = this.moviesDataRepository.getPersonDetails(MoviesEntityFactory.randomInt())
                .test()

        observer.assertComplete()
    }

    @Test
    fun getPersonDetailsReturnsData() {
        val personEntity = MoviesEntityFactory.makePersonEntity()
        val person = MoviesEntityFactory.makePerson()
        this.stubGetPersonDetails(Observable.just(personEntity))
        this.stubPersonMapper(personEntity, person)

        val observer = this.moviesDataRepository.getPersonDetails(MoviesEntityFactory.randomInt())
                .test()

        observer.assertValue(person)
    }

    private fun stubMovieGroupMapper(movieGroupEntity: MovieGroupEntity, movieGroup: MovieGroup) {
        whenever(this.movieGroupEntityMapper.mapFromEntity(movieGroupEntity))
                .thenReturn(movieGroup)
    }

    private fun stubMovieMapper(movieEntity: MovieEntity, movie: Movie) {
        whenever(this.movieEntityMapper.mapFromEntity(movieEntity))
                .thenReturn(movie)
    }

    private fun stubMovieToMapper(movieEntity: MovieEntity, movie: Movie) {
        whenever(this.movieEntityMapper.mapToEntity(movie))
                .thenReturn(movieEntity)
    }

    private fun stubPersonMapper(personEntity: PersonEntity, person: Person) {
        whenever(this.personEntityMapper.mapFromEntity(personEntity))
                .thenReturn(person)
    }

    private fun stubCacheDataStore() {
        whenever(this.factory.getCacheDataStore())
                .thenReturn(this.store)
    }

    private fun stubRemoteDataStore() {
        whenever(this.factory.getRemoteDataStore())
                .thenReturn(this.store)
    }

    private fun stubGetMovieGroups(observable: Observable<List<MovieGroupEntity>>) {
        whenever(this.store.getMovieGroups())
                .thenReturn(observable)
    }

    private fun stubGetMovieDetails(observable: Observable<MovieEntity>) {
        whenever(this.store.getMovieDetails(any()))
                .thenReturn(observable)
    }

    private fun stubGetMovies(observable: Observable<List<MovieEntity>>) {
        whenever(this.store.getMovies(any(), any()))
                .thenReturn(observable)
    }

    private fun stubAddMovieWatchList(completable: Completable) {
        whenever(this.store.addMovieWatchList(any()))
                .thenReturn(completable)
    }

    private fun stubRemoveMovieWatchList(completable: Completable) {
        whenever(this.store.removeMovieWatchlist(any()))
                .thenReturn(completable)
    }

    private fun stubGetWatchListMovie(observable: Observable<List<MovieEntity>>) {
        whenever(this.store.getWatchListMovies())
                .thenReturn(observable)
    }

    private fun stubGetPersonDetails(observable: Observable<PersonEntity>) {
        whenever(this.store.getPersonDetails(any()))
                .thenReturn(observable)
    }
}