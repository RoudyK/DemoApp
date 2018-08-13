package com.roudykk.domain.interactor.browse

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.model.MovieGroup
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Observable
import org.junit.Test

class GetMoviesGroupsTest {

    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val getMoviesGroups = GetMoviesGroups(this.postExecutionThread, this.moviesRepository)

    @Test
    fun getMovieGroupsCompletes() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        val observer = this.getMoviesGroups.buildUseCase().test()
        observer.assertComplete()
    }

    @Test
    fun getMovieGroupsReturnsData() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))

        val observer = this.getMoviesGroups.buildUseCase().test()
        observer.assertValue(movieGroups)
    }

    private fun stubGetMovieGroups(observable: Observable<List<MovieGroup>>) {
        whenever(this.moviesRepository.getMovieGroups())
                .thenReturn(observable)
    }
}