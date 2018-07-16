package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MoviesResult(
        var page: Int,
        var total_results: Int,
        var total_pages: Int,
        var results: MutableList<Movie>
) : Parcelable, Serializable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.createTypedArrayList(Movie.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeInt(total_results)
        writeInt(total_pages)
        writeTypedList(results)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MoviesResult> = object : Parcelable.Creator<MoviesResult> {
            override fun createFromParcel(source: Parcel): MoviesResult = MoviesResult(source)
            override fun newArray(size: Int): Array<MoviesResult?> = arrayOfNulls(size)
        }
    }
}