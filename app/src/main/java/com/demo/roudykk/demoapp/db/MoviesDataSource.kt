package com.demo.roudykk.demoapp.db

import android.arch.paging.PositionalDataSource
import android.util.Log
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.extensions.initThreads
import io.reactivex.Observable

class MoviesDataSource(private var moviesResult: MoviesResult?) : PositionalDataSource<Movie>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Movie>) {
        Log.d("LOAD", "MORE" + " " + params.startPosition + " " + params.loadSize)
        moviesResult?.executor?.getMovies(params.startPosition / 20 + 1)
                ?.initThreads()
                ?.onErrorResumeNext(Observable.empty())
                ?.doOnNext { newResult ->
                    moviesResult?.results?.addAll(newResult.results)
                    callback.onResult(newResult.results)
                }
                ?.subscribe()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Movie>) {
        callback.onResult(moviesResult!!.results, 0, 20)
    }

}