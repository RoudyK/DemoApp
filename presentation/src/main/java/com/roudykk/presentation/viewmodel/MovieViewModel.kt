package com.roudykk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roudykk.domain.interactor.browse.GetMovieDetails
import com.roudykk.domain.interactor.watchlist.AddMovieWatchList
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MovieViewModel @Inject constructor(
        private val getMovieDetails: GetMovieDetails,
        private val addMovieWatchList: AddMovieWatchList,
        private val movieViewMapper: MovieViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<MovieView>>()

    override fun onCleared() {
        this.getMovieDetails.dispose()
        this.addMovieWatchList.dispose()
        super.onCleared()
    }

    fun getMovie(): LiveData<Resource<MovieView>> {
        return this.liveData
    }

    fun fetchMovie(movieId: Int) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.getMovieDetails.execute(MovieObserver(), GetMovieDetails.Params(movieId))
    }

    fun addMovieWatchList(movieView: MovieView) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.addMovieWatchList.execute(MovieCompletableObserver(),
                AddMovieWatchList.Params(this.movieViewMapper.mapFromView(movieView)))
    }

    inner class MovieCompletableObserver : DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }

    inner class MovieObserver : DisposableObserver<Movie>() {
        override fun onComplete() {}

        override fun onNext(data: Movie) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    movieViewMapper.mapToView(data), null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }
}