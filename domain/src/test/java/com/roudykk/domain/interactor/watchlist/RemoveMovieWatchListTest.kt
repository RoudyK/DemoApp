package com.roudykk.domain.interactor.watchlist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Completable
import org.junit.Test

class RemoveMovieWatchListTest {

    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val removeMovieWatchList = RemoveMovieWatchList(this.postExecutionThread, this.moviesRepository)

    @Test
    fun removeMovieWatchListCompletes() {
        this.stubRemoveMovieWatchList(Completable.complete())

        val observer = this.removeMovieWatchList.buildUseCase(RemoveMovieWatchList.Params(
                MoviesFactory.randomInt()
        )).test()

        observer.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeMovieWatchListThrowsException() {
        this.stubRemoveMovieWatchList(Completable.complete())

        val observer = this.removeMovieWatchList.buildUseCase().test()

        observer.assertComplete()
    }

    private fun stubRemoveMovieWatchList(completable: Completable) {
        whenever(this.moviesRepository.removeMovieWatchList(any()))
                .thenReturn(completable)
    }
}