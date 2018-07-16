package com.demo.roudykk.demoapp.api.executor

import android.os.Parcel
import android.os.Parcelable
import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.model.MoviesResult
import io.reactivex.Observable

class MostPopularKidsRequest() : MoviesRequest() {
    override var title: String = "Most Popular (Kids)"

    override lateinit var moviesResult: MoviesResult

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        moviesResult = parcel.readParcelable(MoviesResult::class.java.classLoader)
    }

    override fun getMovies(page: Int): Observable<MoviesResult> {
        return Api.discoverApi().getMostPopularForKids(page)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeParcelable(moviesResult, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MostPopularKidsRequest> {
        override fun createFromParcel(parcel: Parcel): MostPopularKidsRequest {
            return MostPopularKidsRequest(parcel)
        }

        override fun newArray(size: Int): Array<MostPopularKidsRequest?> {
            return arrayOfNulls(size)
        }
    }
}