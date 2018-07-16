package com.demo.roudykk.demoapp.api.models

import android.os.Parcel
import android.os.Parcelable

data class VideoResult(
        var results: ArrayList<Video>
) : Parcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(Video.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(results)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VideoResult> = object : Parcelable.Creator<VideoResult> {
            override fun createFromParcel(source: Parcel): VideoResult = VideoResult(source)
            override fun newArray(size: Int): Array<VideoResult?> = arrayOfNulls(size)
        }
    }
}