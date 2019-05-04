package com.roudykk.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.browse.GetMoviesGroups
import com.roudykk.domain.model.MovieGroup
import com.roudykk.presentation.mapper.MovieGroupViewMapper
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getMoviesGroups = mock<GetMoviesGroups>()
    private val mapper = mock<MovieGroupViewMapper>()

    private val viewModel = HomeViewModel(this.getMoviesGroups, this.mapper)


    @Captor
    private val captor = argumentCaptor<DisposableObserver<List<MovieGroup>>>()

    @Test
    fun getMovieGroupsExecutes() {
        this.viewModel.fetchMovieGroups()

        verify(this.getMoviesGroups, times(1)).execute(any(), eq(null))
    }

    @Test
    fun getMovieGroupsReturnsSuccess() {
        val movieGroup = MoviesViewFactory.makeMovieGroup()
        val movieGroupView = MoviesViewFactory.makeMovieGroupView()

        this.stubMovieGroupMapper(movieGroupView, movieGroup)

        this.viewModel.fetchMovieGroups()
        verify(this.getMoviesGroups).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onNext(listOf(movieGroup))

        assertEquals(ResourceState.SUCCESS, this.viewModel.getMovieGroups().value?.status)
    }

    @Test
    fun getMovieGroupsReturnsData() {
        val movieGroup = MoviesViewFactory.makeMovieGroup()
        val movieGroupView = MoviesViewFactory.makeMovieGroupView()

        this.stubMovieGroupMapper(movieGroupView, movieGroup)

        this.viewModel.fetchMovieGroups()
        verify(this.getMoviesGroups).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onNext(listOf(movieGroup))

        assertEquals(listOf(movieGroupView), this.viewModel.getMovieGroups().value?.data)
    }

    @Test
    fun getMovieGroupsReturnsError() {
        val movieGroup = MoviesViewFactory.makeMovieGroup()
        val movieGroupView = MoviesViewFactory.makeMovieGroupView()

        this.stubMovieGroupMapper(movieGroupView, movieGroup)

        this.viewModel.fetchMovieGroups()
        verify(this.getMoviesGroups).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getMovieGroups().value?.status)
    }

    @Test
    fun getMovieGroupsReturnsErrorMessage() {
        val movieGroup = MoviesViewFactory.makeMovieGroup()
        val movieGroupView = MoviesViewFactory.makeMovieGroupView()
        val message = MoviesViewFactory.randomString()

        this.stubMovieGroupMapper(movieGroupView, movieGroup)

        this.viewModel.fetchMovieGroups()
        verify(this.getMoviesGroups).execute(this.captor.capture(), eq(null))
        this.captor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getMovieGroups().value?.message)
    }

    private fun stubMovieGroupMapper(movieGroupView: MovieGroupView, movieGroup: MovieGroup) {
        whenever(this.mapper.mapToView(movieGroup))
                .thenReturn(movieGroupView)
    }
}