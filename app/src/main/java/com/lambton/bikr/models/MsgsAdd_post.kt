package com.lambton.mohit.model

import android.os.Parcel
import android.os.Parcelable

data class MsgsAdd_post(val uid: String?,
                        val post_id: String?,
                        val jobTitle: String?,
                        val jobDescription: String?,
                        val jobLoc:String?,
                        val jobDate: String?,
                        val jobType: String?,
                        val jobPackage: String?,
                        val isFavourite: Boolean) : Parcelable{
    var isFavoue = isFavourite
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
        isFavoue = parcel.readByte() != 0.toByte()
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
        parcel.writeByte(if (isFavoue) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MsgsAdd_post> {
        override fun createFromParcel(parcel: Parcel): MsgsAdd_post {
            return MsgsAdd_post(parcel)
        }

        override fun newArray(size: Int): Array<MsgsAdd_post?> {
            return arrayOfNulls(size)
        }
    }
}

