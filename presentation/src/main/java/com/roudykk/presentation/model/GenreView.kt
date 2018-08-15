package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class GenreView(var id: Int? = null,
                     var name: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GenreView> = object : Parcelable.Creator<GenreView> {
            override fun createFromParcel(source: Parcel): GenreView = GenreView(source)
            override fun newArray(size: Int): Array<GenreView?> = arrayOfNulls(size)
        }
    }
}