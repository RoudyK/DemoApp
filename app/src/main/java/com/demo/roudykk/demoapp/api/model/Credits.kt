package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class Credits(
        var cast: ArrayList<Person>? = null,
        var crew: ArrayList<Person>? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(Person.CREATOR),
            source.createTypedArrayList(Person.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(cast)
        writeTypedList(crew)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Credits> = object : Parcelable.Creator<Credits> {
            override fun createFromParcel(source: Parcel): Credits = Credits(source)
            override fun newArray(size: Int): Array<Credits?> = arrayOfNulls(size)
        }
    }
}