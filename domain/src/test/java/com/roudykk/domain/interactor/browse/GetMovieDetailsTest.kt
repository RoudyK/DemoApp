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

class GetMovieDetailsTest {

    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private var getMovieDetails = GetMovieDetails(this.postExecutionThread, this.moviesRepository)

    @Test
    fun getMovieDetailsCompletes() {
        this.stubGetMovieDetails(Observable.just(MoviesFactory.makeMovie()))

        val observer = this.getMovieDetails.buildUseCase(GetMovieDetails
                .Params(MoviesFactory.randomInt())).test()

        observer.assertComplete()
    }

    @Test
    fun getMovieDetailsReturnsData() {
        val movie = MoviesFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movie))

        val observer = this.getMovieDetails.buildUseCase(GetMovieDetails
                .Params(MoviesFactory.randomInt())).test()

        observer.assertValue(movie)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getMovieDetailsThrowsException() {
        val movie = MoviesFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movie))

        val observer = this.getMovieDetails.buildUseCase().test()

        observer.assertComplete()
    }

    private fun stubGetMovieDetails(observable: Observable<Movie>) {
        whenever(this.moviesRepository.getMovieDetails(any()))
                .thenReturn(observable)
    }
}