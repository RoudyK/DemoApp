package com.demo.roudykk.demoapp.api.executor

import android.os.Parcel
import android.os.Parcelable
import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.extensions.year
import io.reactivex.Observable
import java.util.*

class MostPopularYearRequest() : MoviesRequest() {
    override var title: String = "Most Popular (${Calendar.getInstance().year()})"

    override lateinit var moviesResult: MoviesResult

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        moviesResult = parcel.readParcelable(MoviesResult::class.java.classLoader)
    }

    override fun getMovies(page: Int): Observable<MoviesResult> {
        return Api.discoverApi().getMostPopularInYear(page, Calendar.getInstance().year())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeParcelable(moviesResult, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MostPopularYearRequest> {
        override fun createFromParcel(parcel: Parcel): MostPopularYearRequest {
            return MostPopularYearRequest(parcel)
        }

        override fun newArray(size: Int): Array<MostPopularYearRequest?> {
            return arrayOfNulls(size)
        }
    }
}