package com.lambton.mohit.model

import android.os.Parcel
import android.os.Parcelable

data class Add_post(val uid: String?,
                    val post_id: String?,
                    val jobTitle: String?,
                    val jobDescription: String?,
                    val jobLoc:String?,
                    val jobDate: String?,
                    val jobType: String?,
                    val jobPackage: String?,
                    val isFavourite: Boolean,
val fav: Favourite_post?,
                    val applied: Applied_post?) : Parcelable{
    var isFavoue = isFavourite
    var favPost = fav
    var appliedPosts = applied
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Favourite_post::class.java.classLoader),
        parcel.readParcelable(Applied_post::class.java.classLoader)
    ) {
        isFavoue = parcel.readByte() != 0.toByte()
        favPost = parcel.readParcelable(Favourite_post::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(post_id)
        parcel.writeString(jobTitle)
        parcel.writeString(jobDescription)
        parcel.writeString(jobLoc)
        parcel.writeString(jobDate)
        parcel.writeString(jobType)
        parcel.writeString(jobPackage)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeParcelable(fav, flags)
        parcel.writeParcelable(applied, flags)
        parcel.writeByte(if (isFavoue) 1 else 0)
        parcel.writeParcelable(favPost, flags)
        parcel.writeParcelable(appliedPosts, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Add_post> {
        override fun createFromParcel(parcel: Parcel): Add_post {
            return Add_post(parcel)
        }

        override fun newArray(size: Int): Array<Add_post?> {
            return arrayOfNulls(size)
        }
    }
}

