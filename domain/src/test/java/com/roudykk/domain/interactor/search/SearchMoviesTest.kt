package com.roudykk.domain.interactor.search

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Observable
import org.junit.Test

class SearchMoviesTest {

    private val thread = mock<PostExecutionThread>()
    private val moviesRepository = mock<MoviesRepository>()
    private val searchMovies = SearchMovies(this.thread, this.moviesRepository)

    @Test
    fun searchMoviesCompletes() {
        val movie = MoviesFactory.makeMovie()
        this.stubSearchMovies(Observable.just(listOf(movie)))

        val observer = this.searchMovies.buildUseCase(SearchMovies
                .Params(MoviesFactory.randomString())).test()

        observer.assertComplete()
    }

    @Test
    fun searchMoviesReturnsData() {
        val movie = MoviesFactory.makeMovie()
        this.stubSearchMovies(Observable.just(listOf(movie)))

        val observer = this.searchMovies.buildUseCase(SearchMovies
                .Params(MoviesFactory.randomString())).test()

        observer.assertValue(listOf(movie))
    }

    @Test(expected = IllegalArgumentException::class)
    fun searchMoviesThrowsException() {
        val movie = MoviesFactory.makeMovie()
        this.stubSearchMovies(Observable.just(listOf(movie)))

        val observer = this.searchMovies.buildUseCase().test()

        observer.assertComplete()
    }

    private fun stubSearchMovies(observable: Observable<List<Movie>>) {
        whenever(this.moviesRepository.searchMovies(any()))
                .thenReturn(observable)
    }
}