package com.roudykk.data.store

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.data.model.MovieEntity
import com.roudykk.data.repository.MoviesCache
import com.roudykk.data.test.MoviesEntityFactory
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class MoviesCacheDataStoreTest {

    private val cache = mock<MoviesCache>()
    private val dataStore = MoviesCacheDataStore(this.cache)

    @Test(expected = UnsupportedOperationException::class)
    fun getMovieGroupsThrowsException() {
        this.dataStore.getMovieGroups()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getMovieDetailsThrowsException() {
        this.dataStore.getMovieDetails(MoviesEntityFactory.randomInt())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getMovieThrowsException() {
        this.dataStore.getMovies(MoviesEntityFactory.randomString(), MoviesEntityFactory.randomInt())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getPersonDetailsThrowsException() {
        this.dataStore.getPersonDetails(MoviesEntityFactory.randomInt())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun searchMoviesThrowsException() {
        this.dataStore.searchMovies(MoviesEntityFactory.randomString())
    }

    @Test
    fun addMovieWatchListCompletes() {
        this.stubAddMovieWatchList(Completable.complete())

        val observer = this.dataStore.addMovieWatchList(MoviesEntityFactory.makeMovieEntity()).test()

        observer.assertComplete()
    }

    @Test
    fun addMovieWatchListCallsCache() {
        val movie = MoviesEntityFactory.makeMovieEntity()
        this.stubAddMovieWatchList(Completable.complete())

        this.dataStore.addMovieWatchList(movie)

        verify(this.cache).addMovieWatchList(movie)
    }

    @Test
    fun removeMovieWatchListCompletes() {
        this.stubRemoveMovieWatchList(Completable.complete())

        val observer = this.dataStore.removeMovieWatchlist(MoviesEntityFactory.randomInt()).test()

        observer.assertComplete()
    }

    @Test
    fun removeMovieWatchListCallsCache() {
        val movieId = MoviesEntityFactory.randomInt()
        this.stubRemoveMovieWatchList(Completable.complete())

        this.dataStore.removeMovieWatchlist(movieId)

        verify(this.cache).removeMovieWatchlist(movieId)
    }

    @Test
    fun getWatchListMoviesCompletes() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubGetWatchListMovies(Observable.just(movies))

        val observer = this.dataStore.getWatchListMovies().test()

        observer.assertComplete()
    }

    @Test
    fun getWatchListMoviesReturnsData() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubGetWatchListMovies(Observable.just(movies))

        val observer = this.dataStore.getWatchListMovies().test()

        observer.assertValue(movies)
    }

    @Test
    fun getWatchListMoviesCallsCache() {
        val movies = listOf(MoviesEntityFactory.makeMovieEntity())
        this.stubGetWatchListMovies(Observable.just(movies))

        this.dataStore.getWatchListMovies().test()

        verify(this.cache).getWatchListMovies()
    }

    @Test
    fun clearWatchListMoviesCompletes() {
        this.stubClearWatchListMovies(Completable.complete())

        val observer = this.dataStore.clearWatchListMovies().test()

        observer.assertComplete()
    }

    @Test
    fun clearWatchListMoviesCallsCache() {
        this.stubClearWatchListMovies(Completable.complete())

        this.dataStore.clearWatchListMovies().test()

        verify(this.cache).clearWatchListMovies()
    }

    private fun stubAddMovieWatchList(completable: Completable) {
        whenever(this.cache.addMovieWatchList(any()))
                .thenReturn(completable)
    }

    private fun stubRemoveMovieWatchList(completable: Completable) {
        whenever(this.cache.removeMovieWatchlist(any()))
                .thenReturn(completable)
    }

    private fun stubGetWatchListMovies(observable: Observable<List<MovieEntity>>) {
        whenever(this.cache.getWatchListMovies())
                .thenReturn(observable)
    }

    private fun stubClearWatchListMovies(completable: Completable) {
        whenever(this.cache.clearWatchListMovies())
                .thenReturn(completable)
    }
}