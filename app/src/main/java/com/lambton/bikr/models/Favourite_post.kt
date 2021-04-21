package com.lambton.mohit.model

import android.os.Parcel
import android.os.Parcelable

data class Favourite_post(val uid: String?,
                          val post_id: String?,
                          val isFavourite: Boolean):Parcelable{
    var isFavour = this.isFavourite
    var postID = this.post_id
    var userID = this.uid

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
        isFavour = parcel.readByte() != 0.toByte()
        postID = parcel.readString()
        userID = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(post_id)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeByte(if (isFavour) 1 else 0)
        parcel.writeString(postID)
        parcel.writeString(userID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favourite_post> {
        override fun createFromParcel(parcel: Parcel): Favourite_post {
            return Favourite_post(parcel)
        }

        override fun newArray(size: Int): Array<Favourite_post?> {
            return arrayOfNulls(size)
        }
    }
}

