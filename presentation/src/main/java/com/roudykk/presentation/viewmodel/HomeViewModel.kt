package com.roudykk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roudykk.domain.interactor.browse.GetMoviesGroups
import com.roudykk.domain.model.MovieGroup
import com.roudykk.presentation.mapper.MovieGroupViewMapper
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val getMoviesGroups: GetMoviesGroups,
        private val movieGroupViewMapper: MovieGroupViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<MovieGroupView>>>()

    override fun onCleared() {
        this.getMoviesGroups.dispose()
        super.onCleared()
    }

    fun getMovieGroups(): LiveData<Resource<List<MovieGroupView>>> {
        return this.liveData
    }

    fun fetchMovieGroups() {
        if (this.liveData.value == null || this.liveData.value!!.data.isNullOrEmpty()) {
            this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
            this.getMoviesGroups.execute(MovieGroupsObserver())
        } else {
            this.liveData.postValue(Resource(ResourceState.SUCCESS, this.liveData.value?.data, null))
        }
    }

    inner class MovieGroupsObserver : DisposableObserver<List<MovieGroup>>() {
        override fun onComplete() {}

        override fun onNext(data: List<MovieGroup>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, data.map {
                movieGroupViewMapper.mapToView(it)
            }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }

}