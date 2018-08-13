package com.roudykk.domain.interactor.watchlist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Completable
import org.junit.Test

class AddMovieWatchListTest {

    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val addMovieWatchList = AddMovieWatchList(this.postExecutionThread, this.moviesRepository)

    @Test
    fun addMovieWatchListCompletes() {
        this.stubAddMovieWatchList(Completable.complete())

        val observer = this.addMovieWatchList.buildUseCase(AddMovieWatchList.Params(
                MoviesFactory.makeMovie()
        )).test()

        observer.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addMovieWatchListThrowsException() {
        this.stubAddMovieWatchList(Completable.complete())

        val observer = this.addMovieWatchList.buildUseCase().test()

        observer.assertComplete()
    }

    private fun stubAddMovieWatchList(completable: Completable) {
        whenever(this.moviesRepository.addMovieWatchList(any()))
                .thenReturn(completable)
    }
}