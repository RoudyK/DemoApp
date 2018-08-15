package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class ProductionCompanyView(
        var id: Int,
        var logoPath: String? = null,
        var name: String? = null,
        var originCountry: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(logoPath)
        writeString(name)
        writeString(originCountry)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductionCompanyView> = object : Parcelable.Creator<ProductionCompanyView> {
            override fun createFromParcel(source: Parcel): ProductionCompanyView = ProductionCompanyView(source)
            override fun newArray(size: Int): Array<ProductionCompanyView?> = arrayOfNulls(size)
        }
    }
}