package com.roudykk.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.browse.GetMovieDetails
import com.roudykk.domain.interactor.watchlist.AddMovieWatchList
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class MovieViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMovieDetails = mock<GetMovieDetails>()
    private val addMovieWatchList = mock<AddMovieWatchList>()
    private val movieViewMapper = mock<MovieViewMapper>()
    private val viewModel = MovieViewModel(this.getMovieDetails, this.addMovieWatchList,
            this.movieViewMapper)

    @Captor
    private val captor = argumentCaptor<DisposableObserver<Movie>>()

    @Captor
    private val compCaptor = argumentCaptor<DisposableCompletableObserver>()

    @Test
    fun getMovieExecutes() {
        this.viewModel.fetchMovie(MoviesViewFactory.randomInt())

        verify(this.getMovieDetails).execute(any(), any())
    }

    @Test
    fun getMovieReturnsSuccess() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovie(MoviesViewFactory.randomInt())

        verify(this.getMovieDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(movie)

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovie().value?.status)
    }

    @Test
    fun getMovieReturnsData() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovie(MoviesViewFactory.randomInt())

        verify(this.getMovieDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(movie)

        assertEquals(movieView, this.viewModel.getMovie().value?.data)
    }

    @Test
    fun getMovieReturnsError() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovie(MoviesViewFactory.randomInt())

        verify(this.getMovieDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovie().value?.status)
    }

    @Test
    fun getMovieReturnsErrorMessage() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        val message = MoviesViewFactory.randomString()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovie(MoviesViewFactory.randomInt())

        verify(this.getMovieDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovie().value?.message)
    }


    @Test
    fun addMovieWatchListExecutes() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieFromMapper(movieView, movie)
        this.viewModel.addMovieWatchList(movieView)

        verify(this.addMovieWatchList).execute(any(), any())
    }

    @Test
    fun addMovieWatchListReturnsSuccess() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieFromMapper(movieView, movie)
        this.viewModel.addMovieWatchList(movieView)

        verify(this.addMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onComplete()

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovie().value?.status)
    }

    @Test
    fun addMovieWatchListReturnsError() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieFromMapper(movieView, movie)
        this.viewModel.addMovieWatchList(movieView)

        verify(this.addMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovie().value?.status)
    }

    @Test
    fun addMovieWatchListReturnsErrorMessage() {
        val movieView = MoviesViewFactory.makeMovieView()
        val movie = MoviesViewFactory.makeMovie()
        this.stubMovieFromMapper(movieView, movie)
        this.viewModel.addMovieWatchList(movieView)
        val message = MoviesViewFactory.randomString()

        verify(this.addMovieWatchList).execute(this.compCaptor.capture(), any())
        this.compCaptor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovie().value?.message)
    }

    private fun stubMovieMapper(movieView: MovieView, movie: Movie) {
        whenever(this.movieViewMapper.mapToView(movie))
                .thenReturn(movieView)
    }

    private fun stubMovieFromMapper(movieView: MovieView, movie: Movie) {
        whenever(this.movieViewMapper.mapFromView(movieView))
                .thenReturn(movie)
    }
}