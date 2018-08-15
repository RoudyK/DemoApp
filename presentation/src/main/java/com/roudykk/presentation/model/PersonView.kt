package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class PersonView(
        var id: Int,
        var name: String? = null,
        var castId: String? = null,
        var creditId: String? = null,
        var character: String? = null,
        var gender: Int,
        var order: Int,
        var profilePath: String? = null,
        var birthday: String? = null,
        var deathday: String? = null,
        var knownFor: String? = null,
        var biography: String? = null,
        var popularity: Float,
        var placeOfBirth: String? = null,
        var adult: Boolean,
        var knownAs: ArrayList<String>? = null) : Parcelable {

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$profilePath"
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
        writeString(castId)
        writeString(creditId)
        writeString(character)
        writeInt(gender)
        writeInt(order)
        writeString(profilePath)
        writeString(birthday)
        writeString(deathday)
        writeString(knownFor)
        writeString(biography)
        writeFloat(popularity)
        writeString(placeOfBirth)
        writeInt((if (adult) 1 else 0))
        writeStringList(knownAs)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PersonView> = object : Parcelable.Creator<PersonView> {
            override fun createFromParcel(source: Parcel): PersonView = PersonView(source)
            override fun newArray(size: Int): Array<PersonView?> = arrayOfNulls(size)
        }
    }
}