package com.roudykk.data.store

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import com.roudykk.data.repository.MoviesRemote
import com.roudykk.data.test.MoviesEntityFactory
import io.reactivex.Observable
import org.junit.Test

class MoviesRemoteDataStoreTest {

    private val remote = mock<MoviesRemote>()
    private val dataStore = MoviesRemoteDataStore(this.remote)

    @Test(expected = UnsupportedOperationException::class)
    fun addMovieWatchListThrowsException() {
        this.dataStore.addMovieWatchList(MoviesEntityFactory.makeMovieEntity())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun removeMovieWatchListThrowsException() {
        this.dataStore.removeMovieWatchlist(MoviesEntityFactory.randomInt())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getWatchListMoviesThrowsException() {
        this.dataStore.getWatchListMovies()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearWatchListMoviesThrowsException() {
        this.dataStore.clearWatchListMovies()
    }

    @Test
    fun getMovieGroupsCompletes() {
        val movieGroups = MoviesEntityFactory.makeMovieGroupsEntity()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        val observer = this.dataStore.getMovieGroups().test()

        observer.assertComplete()
    }

    @Test
    fun getMovieGroupsReturnsData() {
        val movieGroups = MoviesEntityFactory.makeMovieGroupsEntity()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        val observer = this.dataStore.getMovieGroups().test()

        observer.assertValue(movieGroups)
    }

    @Test
    fun getMovieGroupsCallsRemote() {
        val movieGroups = MoviesEntityFactory.makeMovieGroupsEntity()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        this.dataStore.getMovieGroups().test()

        verify(this.remote).getMovieGroups()
    }

    @Test
    fun getMoviesDetailsCompletes() {
        val movie = MoviesEntityFactory.makeMovieEntity()
        this.stubGetMovieDetails(Observable.just(movie))

        val observer = this.dataStore.getMovieDetails(MoviesEntityFactory
                .randomInt()).test()

        observer.assertComplete()
    }

    @Test
    fun getMoviesDetailsReturnsData() {
        val movie = MoviesEntityFactory.makeMovieEntity()
        this.stubGetMovieDetails(Observable.just(movie))

        val observer = this.dataStore.getMovieDetails(MoviesEntityFactory
                .randomInt()).test()

        observer.assertValue(movie)
    }

    @Test
    fun getMoviesDetailsCallsCache() {
        val movie = MoviesEntityFactory.makeMovieEntity()
        val movieId = MoviesEntityFactory.randomInt()
        this.stubGetMovieDetails(Observable.just(movie))

        this.dataStore.getMovieDetails(movieId).test()

        verify(this.remote).getMovieDetails(movieId)
    }

    @Test
    fun getMoviesCompletes() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubGetMovies(Observable.just(movies))

        val observer = this.dataStore.getMovies(
                MoviesEntityFactory.randomString(),
                MoviesEntityFactory.randomInt()).test()

        observer.assertComplete()
    }


    @Test
    fun getMoviesReturnsData() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubGetMovies(Observable.just(movies))

        val observer = this.dataStore.getMovies(
                MoviesEntityFactory.randomString(),
                MoviesEntityFactory.randomInt()).test()

        observer.assertValue(movies)
    }

    @Test
    fun getMoviesCallsCache() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        val index = MoviesEntityFactory.randomString()
        val page = MoviesEntityFactory.randomInt()
        this.stubGetMovies(Observable.just(movies))

        this.dataStore.getMovies(index, page).test()

        verify(this.remote).getMovies(index, page)
    }

    @Test
    fun getPersonDetailsCompletes() {
        val person = MoviesEntityFactory.makePersonEntity()
        this.stubGetPersonDetails(Observable.just(person))

        val observer = this.dataStore.getPersonDetails(MoviesEntityFactory.randomInt()).test()

        observer.assertComplete()
    }

    @Test
    fun getPersonDetailsReturnsData() {
        val person = MoviesEntityFactory.makePersonEntity()
        this.stubGetPersonDetails(Observable.just(person))

        val observer = this.dataStore.getPersonDetails(MoviesEntityFactory.randomInt()).test()

        observer.assertValue(person)
    }

    @Test
    fun getPersonDetailsCallsCache() {
        val person = MoviesEntityFactory.makePersonEntity()
        val personId = MoviesEntityFactory.randomInt()
        this.stubGetPersonDetails(Observable.just(person))

        this.dataStore.getPersonDetails(personId).test()

        verify(this.remote).getPersonDetails(personId)
    }

    @Test
    fun searchMoviesCompletes() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubSearchMovies(Observable.just(movies))

        val observer = this.dataStore.searchMovies(MoviesEntityFactory.randomString()).test()

        observer.assertComplete()
    }

    @Test
    fun searchMoviesReturnsData() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubSearchMovies(Observable.just(movies))

        val observer = this.dataStore.searchMovies(MoviesEntityFactory.randomString()).test()

        observer.assertValue(movies)
    }

    @Test
    fun searchMoviesCallsRemote() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubSearchMovies(Observable.just(movies))
        val searchQuery = MoviesEntityFactory.randomString()

        this.dataStore.searchMovies(searchQuery).test()

        verify(this.remote).searchMovies(searchQuery)
    }

    private fun stubGetMovieGroups(observable: Observable<List<MovieGroupEntity>>) {
        whenever(this.remote.getMovieGroups())
                .thenReturn(observable)
    }

    private fun stubGetMovieDetails(observable: Observable<MovieEntity>) {
        whenever(this.remote.getMovieDetails(any()))
                .thenReturn(observable)
    }

    private fun stubGetMovies(observable: Observable<List<MovieEntity>>) {
        whenever(this.remote.getMovies(any(), any()))
                .thenReturn(observable)
    }

    private fun stubGetPersonDetails(observable: Observable<PersonEntity>) {
        whenever(this.remote.getPersonDetails(any()))
                .thenReturn(observable)
    }

    private fun stubSearchMovies(observable: Observable<List<MovieEntity>>) {
        whenever(this.remote.searchMovies(any()))
                .thenReturn(observable)
    }
}