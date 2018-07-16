package com.demo.roudykk.demoapp.api.models

import android.os.Parcel
import android.os.Parcelable

data class ReviewResult(
        var page: Int,
        var results: ArrayList<Review>? = null,
        var total_pages: Int,
        var total_results: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.createTypedArrayList(Review.CREATOR),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeTypedList(results)
        writeInt(total_pages)
        writeInt(total_results)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReviewResult> = object : Parcelable.Creator<ReviewResult> {
            override fun createFromParcel(source: Parcel): ReviewResult = ReviewResult(source)
            override fun newArray(size: Int): Array<ReviewResult?> = arrayOfNulls(size)
        }
    }
}