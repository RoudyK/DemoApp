package com.roudykk.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.roudykk.domain.interactor.browse.GetMovies
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
        private val getMovies: GetMovies,
        private val movieViewMapper: MovieViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<MovieView>>>()

    fun getMovies(): LiveData<Resource<List<MovieView>>> {
        return this.liveData
    }

    fun fetchMovies(index: String, page: Int) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.getMovies.execute(MoviesObserver(), GetMovies.Params(index, page))
    }

    override fun onCleared() {
        this.getMovies.dispose()
        super.onCleared()
    }

    inner class MoviesObserver : DisposableObserver<List<Movie>>() {
        override fun onComplete() {}

        override fun onNext(data: List<Movie>) {
            liveData.postValue(Resource(ResourceState.SUCCESS, data.map {
                movieViewMapper.mapToView(it)
            }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }
}