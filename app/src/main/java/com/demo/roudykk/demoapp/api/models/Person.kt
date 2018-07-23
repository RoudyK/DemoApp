package com.demo.roudykk.demoapp.api.models

import android.os.Parcel
import android.os.Parcelable

data class Person(
        var id: Int,
        var name: String,
        var cast_id: String,
        var credit_id: String,
        var character: String,
        var gender: Int,
        var order: Int,
        var profile_path: String? = null,
        var birthday: String,
        var deathday: String,
        var known_for_department: String,
        var biography: String,
        var popularity: Float,
        var place_of_birth: String,
        var adult: Boolean,
        var also_known_as: ArrayList<String>
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
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readFloat(),
            source.readString(),
            1 == source.readInt(),
            source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(cast_id)
        writeString(credit_id)
        writeString(character)
        writeInt(gender)
        writeInt(order)
        writeString(profile_path)
        writeString(birthday)
        writeString(deathday)
        writeString(known_for_department)
        writeString(biography)
        writeFloat(popularity)
        writeString(place_of_birth)
        writeInt((if (adult) 1 else 0))
        writeStringList(also_known_as)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person = Person(source)
            override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
        }
    }
}