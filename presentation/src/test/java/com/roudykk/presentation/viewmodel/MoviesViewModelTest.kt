package com.roudykk.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.browse.GetMovies
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieIndex
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class MoviesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getMovies = mock<GetMovies>()
    private val mapper = mock<MovieViewMapper>()

    private val viewModel = MoviesViewModel(this.getMovies, this.mapper)


    @Captor
    private val captor = argumentCaptor<DisposableObserver<List<Movie>>>()

    @Test
    fun getMoviesExecutes() {
        val index = MovieIndex.HIGHEST_RATED
        val page = MoviesViewFactory.randomInt()

        this.viewModel.fetchMovies(index, page)

        verify(this.getMovies).execute(any(), any())
    }

    @Test
    fun getMoviesReturnsSuccess() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(MovieIndex.HIGHEST_RATED, MoviesViewFactory.randomInt())
        verify(this.getMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(listOf(movie))

        Assert.assertEquals(ResourceState.SUCCESS, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun getMoviesReturnsData() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(MovieIndex.HIGHEST_RATED, MoviesViewFactory.randomInt())
        verify(this.getMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(listOf(movie))

        Assert.assertEquals(listOf(movieView), this.viewModel.getMovies().value?.data)
    }

    @Test
    fun getMoviesReturnsError() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(MovieIndex.HIGHEST_RATED, MoviesViewFactory.randomInt())
        verify(this.getMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException())

        Assert.assertEquals(ResourceState.ERROR, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun getMoviesReturnsErrorMessage() {
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()
        val message = MoviesViewFactory.randomString()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(MovieIndex.HIGHEST_RATED, MoviesViewFactory.randomInt())
        verify(this.getMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException(message))


        Assert.assertEquals(message, this.viewModel.getMovies().value?.message)
    }

    private fun stubMovieMapper(movieView: MovieView, movie: Movie) {
        whenever(this.mapper.mapToView(movie))
                .thenReturn(movieView)
    }

}