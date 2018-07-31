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
        var birthday: String? = null,
        var deathday: String? = null,
        var known_for_department: String? = null,
        var biography: String? = null,
        var popularity: Float,
        var place_of_birth: String? = null,
        var adult: Boolean,
        var also_known_as: ArrayList<String>,
        var credits: Credits
) : Parcelable {

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$profile_path"
    }

    fun getGender(): String {
        return if (gender == 2) {
            "Male"
        } else {
            "Female"
        }
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
            source.createStringArrayList(),
            source.readParcelable<Credits>(Credits::class.java.classLoader)
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
        writeParcelable(credits, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person = Person(source)
            override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
        }
    }
}