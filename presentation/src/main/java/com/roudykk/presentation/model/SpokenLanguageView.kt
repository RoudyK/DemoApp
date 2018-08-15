package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class SpokenLanguageView(
        var isoName: String? = null,
        var name: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(isoName)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SpokenLanguageView> = object : Parcelable.Creator<SpokenLanguageView> {
            override fun createFromParcel(source: Parcel): SpokenLanguageView = SpokenLanguageView(source)
            override fun newArray(size: Int): Array<SpokenLanguageView?> = arrayOfNulls(size)
        }
    }
}