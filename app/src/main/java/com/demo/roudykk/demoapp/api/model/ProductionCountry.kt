package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

class ProductionCountry(
        var iso_3116_1: String?= null,
        var name: String?= null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(iso_3116_1)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductionCountry> = object : Parcelable.Creator<ProductionCountry> {
            override fun createFromParcel(source: Parcel): ProductionCountry = ProductionCountry(source)
            override fun newArray(size: Int): Array<ProductionCountry?> = arrayOfNulls(size)
        }
    }
}