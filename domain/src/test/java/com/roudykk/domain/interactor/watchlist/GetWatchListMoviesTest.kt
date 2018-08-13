package com.roudykk.domain.interactor.watchlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Observable
import org.junit.Test

class GetWatchListMoviesTest {

    private val postExecutionThread = mock<PostExecutionThread>()
    private val moviesRepository = mock<MoviesRepository>()
    private val getWatchListMovies = GetWatchListMovies(this.postExecutionThread,
            this.moviesRepository)

    @Test
    fun getWatchListMoviesCompletes() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubGetWatchListMovies(Observable.just(movies))

        val observer = this.getWatchListMovies.buildUseCase().test()

        observer.assertComplete()
    }

    @Test
    fun getWatchListMoviesReturnsData() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubGetWatchListMovies(Observable.just(movies))

        val observer = this.getWatchListMovies.buildUseCase().test()

        observer.assertValue(movies)
    }

    private fun stubGetWatchListMovies(observable: Observable<List<Movie>>) {
        whenever(this.moviesRepository.getWatchListMovies())
                .thenReturn(observable)
    }
}