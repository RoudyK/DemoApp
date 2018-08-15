package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

class ReviewView(var id: String,
                 var author: String,
                 var content: String,
                 var url: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(author)
        writeString(content)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReviewView> = object : Parcelable.Creator<ReviewView> {
            override fun createFromParcel(source: Parcel): ReviewView = ReviewView(source)
            override fun newArray(size: Int): Array<ReviewView?> = arrayOfNulls(size)
        }
    }
}