package com.roudykk.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.watchlist.ClearWatchListMovies
import com.roudykk.domain.interactor.watchlist.GetWatchListMovies
import com.roudykk.domain.interactor.watchlist.RemoveMovieWatchList
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class WatchListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getMovies = mock<GetWatchListMovies>()
    private val clearMovies = mock<ClearWatchListMovies>()
    private val removeMovieWatchList = mock<RemoveMovieWatchList>()
    private val mapper = mock<MovieViewMapper>()

    private val viewModel = WatchListViewModel(this.getMovies, this.removeMovieWatchList,
            this.clearMovies, this.mapper)

    @Captor
    private val captor = argumentCaptor<DisposableObserver<List<Movie>>>()

    @Captor
    private val compCaptor = argumentCaptor<DisposableCompletableObserver>()

    @Test
    fun getMoviesExecutes() {
        this.viewModel.fetchMovies()

        verify(this.getMovies).execute(any(), eq(null))
    }

    @Test
    fun getMoviesReturnsSuccess() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies()
        verify(this.getMovies).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onNext(listOf(movie))

        Assert.assertEquals(ResourceState.SUCCESS, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun getMoviesReturnsData() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies()
        verify(this.getMovies).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onNext(listOf(movie))

        Assert.assertEquals(listOf(movieView), this.viewModel.getMovies().value?.data)
    }

    @Test
    fun getMoviesReturnsError() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies()
        verify(this.getMovies).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onError(RuntimeException())

        Assert.assertEquals(ResourceState.ERROR, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun getMoviesReturnsErrorMessage() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()
        val message = MoviesViewFactory.randomString()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies()
        verify(this.getMovies).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onError(RuntimeException(message))


        Assert.assertEquals(message, this.viewModel.getMovies().value?.message)
    }

    @Test
    fun removeMovieWatchListExecutes() {
        this.viewModel.removeMovieWatchList(MoviesViewFactory.randomInt())

        verify(this.removeMovieWatchList).execute(any(), any())
    }

    @Test
    fun removeMovieWatchListReturnsSuccess() {
        this.viewModel.removeMovieWatchList(MoviesViewFactory.randomInt())

        verify(this.removeMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onComplete()

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun removeMovieWatchListReturnsError() {
        this.viewModel.removeMovieWatchList(MoviesViewFactory.randomInt())

        verify(this.removeMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun removeMovieWatchListReturnsErrorMessage() {
        this.viewModel.removeMovieWatchList(MoviesViewFactory.randomInt())
        val message = MoviesViewFactory.randomString()

        verify(this.removeMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovies().value?.message)
    }

    @Test
    fun clearMovieWatchListExecutes() {
        this.viewModel.clearMovieWatchList()

        verify(this.clearMovies).execute(any(), eq(null))
    }

    @Test
    fun clearMovieWatchListReturnsSuccess() {
        this.viewModel.clearMovieWatchList()

        verify(this.clearMovies).execute(this.compCaptor.capture(), eq(null))
        this.compCaptor.firstValue.onComplete()

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun clearMovieWatchListReturnsError() {
        this.viewModel.clearMovieWatchList()

        verify(this.clearMovies).execute(this.compCaptor.capture(), eq(null))
        this.compCaptor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun clearMovieWatchListReturnsErrorMessage() {
        this.viewModel.clearMovieWatchList()
        val message = MoviesViewFactory.randomString()

        verify(this.clearMovies).execute(this.compCaptor.capture(), eq(null))
        this.compCaptor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovies().value?.message)
    }

    private fun stubMovieMapper(movieView: MovieView, movie: Movie) {
        whenever(this.mapper.mapToView(movie))
                .thenReturn(movieView)
    }

}