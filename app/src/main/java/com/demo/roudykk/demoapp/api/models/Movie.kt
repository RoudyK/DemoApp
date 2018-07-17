package com.demo.roudykk.demoapp.api.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class Movie(
        @PrimaryKey
        var id: Int,
        var title: String? = null,
        var overview: String? = null,
        var video: Boolean? = null,
        var vote_count: Int? = null,
        var vote_average: Float,
        var popularity: Float? = null,
        var poster_path: String? = null,
        var original_language: String? = null,
        var original_title: String? = null,
        @Ignore
        var genre_ids: ArrayList<Int>? = null,
        var backdrop_path: String? = null,
        var release_date: String? = null,
        var revenue: Float? = null,
        var runtime: Int? = null,
        @Ignore
        var spoken_languages: ArrayList<SpokenLanguage>? = null,
        var status: String? = null,
        var tagline: String? = null,
        var budget: Float,
        @Ignore
        var genres: ArrayList<Genre>? = null,
        @Ignore
        var production_companies: ArrayList<ProductionCompany>? = null,
        @Ignore
        var production_countries: ArrayList<ProductionCountry>? = null,
        @Ignore
        var videos: VideoResult? = null,
        @Ignore
        var reviews: ReviewResult? = null,
        @Ignore
        var credits: Credits? = null
) : Parcelable {
    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

    constructor() : this(0, null, null, null, null, 0f, null, null,
            null, null, null, null, null, null, null, null, null, null, 0f,
            null, null, null, null)

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readFloat(),
            source.readValue(Float::class.java.classLoader) as Float?,
            source.readString(),
            source.readString(),
            source.readString(),
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            source.readString(),
            source.readString(),
            source.readValue(Float::class.java.classLoader) as Float?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.createTypedArrayList(SpokenLanguage.CREATOR),
            source.readString(),
            source.readString(),
            source.readFloat(),
            source.createTypedArrayList(Genre.CREATOR),
            source.createTypedArrayList(ProductionCompany.CREATOR),
            source.createTypedArrayList(ProductionCountry.CREATOR),
            source.readParcelable<VideoResult>(VideoResult::class.java.classLoader),
            source.readParcelable<ReviewResult>(ReviewResult::class.java.classLoader),
            source.readParcelable<Credits>(Credits::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeString(overview)
        writeValue(video)
        writeValue(vote_count)
        writeFloat(vote_average)
        writeValue(popularity)
        writeString(poster_path)
        writeString(original_language)
        writeString(original_title)
        writeList(genre_ids)
        writeString(backdrop_path)
        writeString(release_date)
        writeValue(revenue)
        writeValue(runtime)
        writeTypedList(spoken_languages)
        writeString(status)
        writeString(tagline)
        writeFloat(budget)
        writeTypedList(genres)
        writeTypedList(production_companies)
        writeTypedList(production_countries)
        writeParcelable(videos, 0)
        writeParcelable(reviews, 0)
        writeParcelable(credits, 0)
    }

    companion object {
        fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}