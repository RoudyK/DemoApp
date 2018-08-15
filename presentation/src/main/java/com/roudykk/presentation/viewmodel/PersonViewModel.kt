package com.roudykk.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.roudykk.domain.interactor.browse.GetPersonDetails
import com.roudykk.domain.model.Person
import com.roudykk.presentation.mapper.PersonViewMapper
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class PersonViewModel @Inject constructor(
        private val getPersonDetails: GetPersonDetails,
        private val personViewMapper: PersonViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<PersonView>>()

    override fun onCleared() {
        this.getPersonDetails.dispose()
        super.onCleared()
    }

    fun getPerson(): LiveData<Resource<PersonView>> {
        return this.liveData
    }

    fun fetchPerson(personId: Int) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.getPersonDetails.execute(PersonObserver(), GetPersonDetails.Params(personId))
    }

    inner class PersonObserver : DisposableObserver<Person>() {
        override fun onComplete() {}

        override fun onNext(data: Person) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    personViewMapper.mapToView(data), null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }
}