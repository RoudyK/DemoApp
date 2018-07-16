package com.demo.roudykk.demoapp.api.requests

import android.os.Parcelable
import com.demo.roudykk.demoapp.api.models.MoviesResult
import io.reactivex.Observable

abstract class MoviesRequest : Parcelable {

    abstract var title: String

    abstract var moviesResult: MoviesResult

    abstract fun getMovies(page: Int): Observable<MoviesResult>

    override fun toString(): String {
        return "MoviesRequest(title='$title', moviesResult=$moviesResult)"
    }
}