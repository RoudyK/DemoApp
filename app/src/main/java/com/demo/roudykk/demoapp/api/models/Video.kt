package com.demo.roudykk.demoapp.api.models

import android.os.Parcel
import android.os.Parcelable

data class Video(
        var id: String,
        var iso_639_1: String,
        var key: String,
        var name: String,
        var site: String,
        var size: String,
        var type: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(iso_639_1)
        writeString(key)
        writeString(name)
        writeString(site)
        writeString(size)
        writeString(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(source: Parcel): Video = Video(source)
            override fun newArray(size: Int): Array<Video?> = arrayOfNulls(size)
        }
    }
}