package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class MovieView(
        var id: Int,
        var title: String? = null,
        var overview: String? = null,
        var video: Boolean? = null,
        var voteCount: Int? = null,
        var voteAverage: Float,
        var popularity: Float? = null,
        var posterPath: String? = null,
        var originalLanguage: String? = null,
        var originalTitle: String? = null,
        var genreIds: List<Int>? = null,
        var backdropPath: String? = null,
        var releaseDate: String? = null,
        var revenue: Float? = null,
        var runtime: Int? = null,
        var spokenLanguages: List<SpokenLanguageView>? = null,
        var status: String? = null,
        var tagLine: String? = null,
        var budget: Float,
        var genres: List<GenreView>? = null,
        var productionCompanies: List<ProductionCompanyView>? = null,
        var productionCountries: List<ProductionCountryView>? = null,
        var videos: List<VideoView>? = null,
        var reviews: List<ReviewView>? = null,
        var cast: List<PersonView>? = null,
        val crew: List<PersonView>? = null) : Parcelable {
    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$posterPath"
    }

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
            source.createTypedArrayList(SpokenLanguageView.CREATOR),
            source.readString(),
            source.readString(),
            source.readFloat(),
            source.createTypedArrayList(GenreView.CREATOR),
            source.createTypedArrayList(ProductionCompanyView.CREATOR),
            source.createTypedArrayList(ProductionCountryView.CREATOR),
            source.createTypedArrayList(VideoView.CREATOR),
            source.createTypedArrayList(ReviewView.CREATOR),
            source.createTypedArrayList(PersonView.CREATOR),
            source.createTypedArrayList(PersonView.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeString(overview)
        writeValue(video)
        writeValue(voteCount)
        writeFloat(voteAverage)
        writeValue(popularity)
        writeString(posterPath)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeList(genreIds)
        writeString(backdropPath)
        writeString(releaseDate)
        writeValue(revenue)
        writeValue(runtime)
        writeTypedList(spokenLanguages)
        writeString(status)
        writeString(tagLine)
        writeFloat(budget)
        writeTypedList(genres)
        writeTypedList(productionCompanies)
        writeTypedList(productionCountries)
        writeTypedList(videos)
        writeTypedList(reviews)
        writeTypedList(cast)
        writeTypedList(crew)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<MovieView> = object : Parcelable.Creator<MovieView> {
            override fun createFromParcel(source: Parcel): MovieView = MovieView(source)
            override fun newArray(size: Int): Array<MovieView?> = arrayOfNulls(size)
        }
    }
}