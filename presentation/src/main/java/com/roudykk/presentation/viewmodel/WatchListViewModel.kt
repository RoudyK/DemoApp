package com.roudykk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roudykk.domain.interactor.watchlist.ClearWatchListMovies
import com.roudykk.domain.interactor.watchlist.GetWatchListMovies
import com.roudykk.domain.interactor.watchlist.RemoveMovieWatchList
import com.roudykk.domain.model.Movie
import com.roudykk.presentation.mapper.MovieViewMapper
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
        private val getWatchListMovies: GetWatchListMovies,
        private val removeMovieWatchList: RemoveMovieWatchList,
        private val clearMovieWatchList: ClearWatchListMovies,
        private val movieViewMapper: MovieViewMapper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<MovieView>>>()

    fun getMovies(): LiveData<Resource<List<MovieView>>> {
        return this.liveData
    }

    override fun onCleared() {
        this.getWatchListMovies.dispose()
        this.removeMovieWatchList.dispose()
        this.clearMovieWatchList.dispose()
        super.onCleared()
    }

    fun fetchMovies() {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.getWatchListMovies.execute(WatchListMoviesObserver())
    }

    fun removeMovieWatchList(movieId: Int) {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.removeMovieWatchList.execute(WatchListCompletableObserver(),
                RemoveMovieWatchList.Params(movieId))
    }

    fun clearMovieWatchList() {
        this.liveData.postValue(Resource(ResourceState.LOADING, null, null))
        this.clearMovieWatchList.execute(WatchListCompletableObserver())
    }

    inner class WatchListCompletableObserver : DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }

    inner class WatchListMoviesObserver : DisposableObserver<List<Movie>>() {
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