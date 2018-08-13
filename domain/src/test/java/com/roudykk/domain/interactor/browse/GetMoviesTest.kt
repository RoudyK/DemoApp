package com.roudykk.domain.interactor.browse

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Observable
import org.junit.Test

class GetMoviesTest {
    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val getMovies = GetMovies(this.postExecutionThread, this.moviesRepository)

    @Test
    fun getMoviesCompletes() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubGetMovies(Observable.just(movies))

        val observer = this.getMovies.buildUseCase(GetMovies.Params(
                MoviesFactory.randomString(),
                MoviesFactory.randomInt()
        )).test()

        observer.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubGetMovies(Observable.just(movies))

        val observer = this.getMovies.buildUseCase(GetMovies.Params(
                MoviesFactory.randomString(),
                MoviesFactory.randomInt()
        )).test()

        observer.assertValue(movies)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getMoviesThrowsException() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubGetMovies(Observable.just(movies))

        val observer = this.getMovies.buildUseCase().test()

        observer.assertValue(movies)
    }

    private fun stubGetMovies(observable: Observable<List<Movie>>) {
        whenever(this.moviesRepository.getMovies(any(), any()))
                .thenReturn(observable)
    }
}