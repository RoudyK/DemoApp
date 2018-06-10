package com.demo.roudykk.demoapp.api.model

import android.os.Parcel
import android.os.Parcelable

data class Post(
        var id: String,
        var user: User,
        var text: String,
        var timestamp: Long,
        var textSize: Float,
        var imageUrls: List<String>,
        var likes: Int,
        var dislikes: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader),
            parcel.readString(),
            parcel.readLong(),
            parcel.readFloat(),
            parcel.createStringArrayList(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(user, flags)
        parcel.writeString(text)
        parcel.writeLong(timestamp)
        parcel.writeFloat(textSize)
        parcel.writeStringList(imageUrls)
        parcel.writeInt(likes)
        parcel.writeInt(dislikes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

}