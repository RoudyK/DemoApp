package com.roudykk.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class ProductionCountryView(
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
        @JvmField
        val CREATOR: Parcelable.Creator<ProductionCountryView> = object : Parcelable.Creator<ProductionCountryView> {
            override fun createFromParcel(source: Parcel): ProductionCountryView = ProductionCountryView(source)
            override fun newArray(size: Int): Array<ProductionCountryView?> = arrayOfNulls(size)
        }
    }
}