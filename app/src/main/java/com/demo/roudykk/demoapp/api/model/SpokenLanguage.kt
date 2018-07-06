package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class SpokenLanguage(
        var iso_639_1: String?= null,
        var name: String?= null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(iso_639_1)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SpokenLanguage> = object : Parcelable.Creator<SpokenLanguage> {
            override fun createFromParcel(source: Parcel): SpokenLanguage = SpokenLanguage(source)
            override fun newArray(size: Int): Array<SpokenLanguage?> = arrayOfNulls(size)
        }
    }
}