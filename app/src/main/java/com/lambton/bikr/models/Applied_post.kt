package com.lambton.mohit.model

import android.os.Parcel
import android.os.Parcelable

data class Applied_post(val uid: String?,
                        val post_id: String?,
                        val isApplied: Boolean):Parcelable{
    var isApp = this.isApplied
    var postID = this.post_id
    var userID = this.uid

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
        isApp = parcel.readByte() != 0.toByte()
        postID = parcel.readString()
        userID = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(post_id)
        parcel.writeByte(if (isApplied) 1 else 0)
        parcel.writeByte(if (isApp) 1 else 0)
        parcel.writeString(postID)
        parcel.writeString(userID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Applied_post> {
        override fun createFromParcel(parcel: Parcel): Applied_post {
            return Applied_post(parcel)
        }

        override fun newArray(size: Int): Array<Applied_post?> {
            return arrayOfNulls(size)
        }
    }
}

