package com.lambton.mohit.model

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable

data class Post_response(val uid: String?,
                         val post_id: String?,
                         val jobTitle: String?,
                         val jobDescription: String?,
                         val jobLoc:String?,
                         val jobDate: String?,
                         val jobType: String?,
                         val jobPackage: String?,
                         val isFavourite: Boolean?,
                         val fav: Favourite_post?,
                         val messageList: ArrayList<Message>) : Parcelable{
    var isFavoue = isFavourite
    var favPost = fav
    var msgsList = messageList
    @SuppressLint("NewApi")
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
      //  this.appliedPosts =  ArrayList<Applied_post>();
  //  parcel.readList(appliedPosts as ArrayList<Applied_post>, Applied_post.class.getClassLoader());
        arrayListOf<Message>().apply {
            parcel.readList(this as ArrayList<*>,Message::class.java.classLoader)
        }
      //  parcel.readParcelableList(Applied_post::class.java.classLoader)
    ) {
        isFavoue = parcel.readByte() != 0.toByte()
        favPost = parcel.readParcelable(Favourite_post::class.java.classLoader)
    }

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(post_id)
        parcel.writeString(jobTitle)
        parcel.writeString(jobDescription)
        parcel.writeString(jobLoc)
        parcel.writeString(jobDate)
        parcel.writeString(jobType)
        parcel.writeString(jobPackage)
        parcel.writeByte(if (isFavourite!!) 1 else 0)
        parcel.writeParcelable(fav, flags)
        if (Build.VERSION.SDK_INT >= 29) {
            parcel.writeParcelableList(messageList, flags)
        } else {
            parcel.writeList(messageList as List<Message>)
        }
        parcel.writeByte(if (isFavoue!!) 1 else 0)
        parcel.writeParcelable(favPost, flags)
        if (Build.VERSION.SDK_INT >= 29) {
            parcel.writeParcelableList(msgsList, flags)
        } else {
            parcel.writeList(msgsList as List<Message>)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post_response> {
        override fun createFromParcel(parcel: Parcel): Post_response {
            return Post_response(parcel)
        }

        override fun newArray(size: Int): Array<Post_response?> {
            return arrayOfNulls(size)
        }
    }
        }




