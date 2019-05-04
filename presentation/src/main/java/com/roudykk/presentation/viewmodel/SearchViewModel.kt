package com.roudykk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roudykk.domain.interactor.search.SearchMovies
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val searchMovies: SearchMovies,
        private val movieViewMapper: MovieViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<MovieView>>>()

    override fun onCleared() {
        this.searchMovies.dispose()
        super.onCleared()
    }

    fun getMovies(): LiveData<Resource<List<MovieView>>> {
        return this.liveData
    }

    fun fetchMovies(searchQuery: String) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.searchMovies.execute(MoviesObserver(), SearchMovies.Params(searchQuery))
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