package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class SpokenLanguageView(
        var isoName: String? = null,
        var name: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(isoName)
        writeString(name)
    }

    companion object {
        fun toReadableString(spoken_languages: List<SpokenLanguageView>?): String? {
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
        val CREATOR: Parcelable.Creator<SpokenLanguageView> = object : Parcelable.Creator<SpokenLanguageView> {
            override fun createFromParcel(source: Parcel): SpokenLanguageView = SpokenLanguageView(source)
            override fun newArray(size: Int): Array<SpokenLanguageView?> = arrayOfNulls(size)
        }
    }
}