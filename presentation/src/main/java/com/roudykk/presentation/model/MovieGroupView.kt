package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class MovieGroupView(val title: String,
                          val movies: MutableList<MovieView>,
                          var page: Int,
                          val totalPages: Int,
                          val index: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()!!,
            source.createTypedArrayList(MovieView.CREATOR)!!,
            source.readInt(),
            source.readInt(),
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeTypedList(movies)
        writeInt(page)
        writeInt(totalPages)
        writeString(index)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieGroupView> = object : Parcelable.Creator<MovieGroupView> {
            override fun createFromParcel(source: Parcel): MovieGroupView = MovieGroupView(source)
            override fun newArray(size: Int): Array<MovieGroupView?> = arrayOfNulls(size)
        }
    }
}