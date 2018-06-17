package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
        var id: Int,
        var title: String,
        var overview: String,
        var video: Boolean,
        var vote_count: Int,
        var vote_average: Float,
        var popularity: Float,
        var poster_path: String,
        var original_language: String,
        var original_title: String,
        var genre_ids: ArrayList<Int>,
        var backdrop_path: String,
        var release_date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString(),
            arrayListOf<Int>().apply {
                parcel.readList(this, Int::class.java.classLoader)
            },
            parcel.readString() ?: "",
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeInt(vote_count)
        parcel.writeFloat(vote_average)
        parcel.writeFloat(popularity)
        parcel.writeString(poster_path)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeList(genre_ids)
        parcel.writeString(backdrop_path)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }
}