package com.lambton.mohit.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class Message(val sender_id: String?,
                   val name: String?,
                   val message: String?,
                   val timestamp: String?) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()

      //  parcel.readParcelableList(Applied_post::class.java.classLoader)
    )

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sender_id)
        parcel.writeString(name)
        parcel.writeString(message)
        parcel.writeString(timestamp)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }
        }




