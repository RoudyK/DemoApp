package com.roudykk.domain.interactor.browse

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.Person
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetPersonDetails @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<Person, GetPersonDetails.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Observable<Person> {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.getPersonDetails(params.personId)
    }

    class Params(val personId: Int)
}