package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class VideoView(
        var id: String,
        var isoName: String,
        var key: String,
        var name: String,
        var site: String,
        var size: String,
        var type: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(isoName)
        writeString(key)
        writeString(name)
        writeString(site)
        writeString(size)
        writeString(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VideoView> = object : Parcelable.Creator<VideoView> {
            override fun createFromParcel(source: Parcel): VideoView = VideoView(source)
            override fun newArray(size: Int): Array<VideoView?> = arrayOfNulls(size)
        }
    }
}