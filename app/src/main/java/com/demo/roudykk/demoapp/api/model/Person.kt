package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class Person(
        var id: Int,
        var cast_id: String,
        var credit_id: String,
        var character: String,
        var name: String,
        var gender: Int,
        var order: Int,
        var profile_path: String? = null
) : Parcelable {

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$profile_path"
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(cast_id)
        writeString(credit_id)
        writeString(character)
        writeString(name)
        writeInt(gender)
        writeInt(order)
        writeString(profile_path)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person = Person(source)
            override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
        }
    }
}