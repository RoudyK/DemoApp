package com.roudykk.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.roudykk.domain.interactor.browse.GetPersonDetails
import com.roudykk.domain.model.Person
import com.roudykk.presentation.mapper.PersonViewMapper
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.test.MoviesViewFactory
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class PersonViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getPersonDetails = mock<GetPersonDetails>()
    private val personViewMapper = mock<PersonViewMapper>()
    private val viewModel = PersonViewModel(this.getPersonDetails, this.personViewMapper)

    @Captor
    private val captor = argumentCaptor<DisposableObserver<Person>>()

    @Test
    fun getPersonExecutes() {
        val personId = MoviesViewFactory.randomInt()
        this.viewModel.fetchPerson(personId)

        verify(this.getPersonDetails).execute(any(), any())
    }

    @Test
    fun getPersonReturnsSuccess() {
        val personId = MoviesViewFactory.randomInt()
        this.viewModel.fetchPerson(personId)

        verify(this.getPersonDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(MoviesViewFactory.makePerson())

        assertEquals(ResourceState.SUCCESS, this.viewModel.getPerson().value?.status)
    }

    @Test
    fun getPersonReturnsData() {
        val person = MoviesViewFactory.makePerson()
        val personView = MoviesViewFactory.makePersonView()

        this.stubPersonMapper(personView, person)

        val personId = MoviesViewFactory.randomInt()
        this.viewModel.fetchPerson(personId)

        verify(this.getPersonDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onNext(person)

        assertEquals(personView, this.viewModel.getPerson().value?.data)
    }

    @Test
    fun getPersonReturnsError() {
        val personId = MoviesViewFactory.randomInt()
        this.viewModel.fetchPerson(personId)

        verify(this.getPersonDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, this.viewModel.getPerson().value?.status)
    }

    @Test
    fun getPersonReturnsErrorMessage() {
        val personId = MoviesViewFactory.randomInt()
        this.viewModel.fetchPerson(personId)
        val message = MoviesViewFactory.randomString()

        verify(this.getPersonDetails).execute(this.captor.capture(), any())
        this.captor.firstValue.onError(RuntimeException(message))

        assertEquals(message, this.viewModel.getPerson().value?.message)
    }

    private fun stubPersonMapper(personView: PersonView, person: Person) {
        whenever(this.personViewMapper.mapToView(person))
                .thenReturn(personView)
    }
}