package com.roudykk.domain.interactor.browse

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.model.Person
import com.roudykk.domain.repository.MoviesRepository
import com.roudykk.domain.test.MoviesFactory
import io.reactivex.Observable
import org.junit.Test

class GetPersonDetailsTest {

    private val moviesRepository = mock<MoviesRepository>()
    private val postExecutionThread = mock<PostExecutionThread>()
    private val getPersonDetails = GetPersonDetails(this.postExecutionThread, this.moviesRepository)

    @Test
    fun getPersonDetailsCompletes() {
        val person = MoviesFactory.makePerson()
        this.stubGetPersonDetails(Observable.just(person))

        val observer = this.getPersonDetails.buildUseCase(GetPersonDetails.Params(
                MoviesFactory.randomInt()
        )).test()
        observer.assertComplete()
    }

    @Test
    fun getPersonDetailsReturnsData() {
        val person = MoviesFactory.makePerson()
        this.stubGetPersonDetails(Observable.just(person))

        val observer = this.getPersonDetails.buildUseCase(GetPersonDetails.Params(
                MoviesFactory.randomInt()
        )).test()
        observer.assertValue(person)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getPersonDetailsThrowsException() {
        val person = MoviesFactory.makePerson()
        this.stubGetPersonDetails(Observable.just(person))

        val observer = this.getPersonDetails.buildUseCase().test()
        observer.assertComplete()
    }

    private fun stubGetPersonDetails(observable: Observable<Person>) {
        whenever(this.moviesRepository.getPersonDetails(any()))
                .thenReturn(observable)
    }

}