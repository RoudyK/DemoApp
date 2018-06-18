package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable
import com.demo.roudykk.demoapp.api.executor.ApiExecutor

data class MoviesResult(
        var executor: ApiExecutor,
        var title: String,
        var page: Int,
        var total_results: Int,
        var total_pages: Int,
        var results: MutableList<Movie>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readSerializable() as ApiExecutor,
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            arrayListOf<Movie>().apply {
                parcel.readList(this, Movie::class.java.classLoader)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(executor)
        parcel.writeString(title)
        parcel.writeInt(page)
        parcel.writeInt(total_results)
        parcel.writeInt(total_pages)
        parcel.writeList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesResult> {
        override fun createFromParcel(parcel: Parcel): MoviesResult {
            return MoviesResult(parcel)
        }

        override fun newArray(size: Int): Array<MoviesResult?> {
            return arrayOfNulls(size)
        }
    }
}