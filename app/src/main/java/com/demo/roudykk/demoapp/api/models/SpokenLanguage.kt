package com.demo.roudykk.demoapp.api.models

import android.os.Parcel
import android.os.Parcelable

data class SpokenLanguage(
        var iso_639_1: String? = null,
        var name: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(iso_639_1)
        writeString(name)
    }

    companion object {
        fun toReadableString(spoken_languages: ArrayList<SpokenLanguage>?): String? {
            val languages = mutableListOf<String>()
            spoken_languages?.forEach { language ->
                if (language.name != null) {
                    languages.add(language.name!!)
                }
            }

            return if (languages.size > 0) {
                languages.joinToString(",")
            } else {
                "Not set"
            }
        }

        @JvmField
        val CREATOR: Parcelable.Creator<SpokenLanguage> = object : Parcelable.Creator<SpokenLanguage> {
            override fun createFromParcel(source: Parcel): SpokenLanguage = SpokenLanguage(source)
            override fun newArray(size: Int): Array<SpokenLanguage?> = arrayOfNulls(size)
        }
    }
}