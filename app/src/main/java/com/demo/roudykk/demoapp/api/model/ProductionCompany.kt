package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class ProductionCompany(
        var id: Int? = null,
        var logo_path: String? = null,
        var name: String? = null,
        var origin_country: String? = null
) : Parcelable {

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$logo_path"
    }

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(logo_path)
        writeString(name)
        writeString(origin_country)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductionCompany> = object : Parcelable.Creator<ProductionCompany> {
            override fun createFromParcel(source: Parcel): ProductionCompany = ProductionCompany(source)
            override fun newArray(size: Int): Array<ProductionCompany?> = arrayOfNulls(size)
        }
    }
}
