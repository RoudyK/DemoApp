package com.roudykk.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.search.SearchMovies
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mapper = mock<MovieViewMapper>()
    private val searchMovies = mock<SearchMovies>()

    private val viewModel = SearchViewModel(this.searchMovies, this.mapper)

    @Captor
    private val captor = argumentCaptor<DisposableObserver<List<Movie>>>()

    @Test
    fun searchMoviesExecutes() {
        val searchQuery = MoviesViewFactory.randomString()

        this.viewModel.fetchMovies(searchQuery)

        verify(this.searchMovies).execute(any(), any())
    }

    @Test
    fun searchMoviesSuccess() {
        val searchQuery = MoviesViewFactory.randomString()
        val movies = listOf(MoviesViewFactory.makeMovie())

        this.viewModel.fetchMovies(searchQuery)

        verify(this.searchMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(movies)

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun searchMoviesReturnsData() {
        val searchQuery = MoviesViewFactory.randomString()
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(searchQuery)

        verify(this.searchMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(listOf(movie))

        assertEquals(listOf(movieView), this.viewModel.getMovies().value?.data)
    }


    @Test
    fun searchMoviesReturnsError() {
        val searchQuery = MoviesViewFactory.randomString()
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(searchQuery)

        verify(this.searchMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovies().value?.status)
    }

    @Test
    fun searchMoviesReturnsErrorMessage() {
        val searchQuery = MoviesViewFactory.randomString()
        val movie = MoviesViewFactory.makeMovie()
        val movieView = MoviesViewFactory.makeMovieView()
        val message = MoviesViewFactory.randomString()

        this.stubMovieMapper(movieView, movie)

        this.viewModel.fetchMovies(searchQuery)

        verify(this.searchMovies).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovies().value?.message)
    }

    private fun stubMovieMapper(movieView: MovieView, movie: Movie) {
        whenever(this.mapper.mapToView(movie))
                .thenReturn(movieView)
    }
}