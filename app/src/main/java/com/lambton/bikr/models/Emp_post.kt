package com.lambton.mohit.model

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable

data class Emp_post(val uid: String?,
                    val post_id: String?,
                    val jobTitle: String?,
                    val jobDescription: String?,
                    val jobLoc:String?,
                    val jobDate: String?,
                    val jobType: String?,
                    val jobPackage: String?,
                    val isFavourite: Boolean,
                    val fav: Favourite_post?,
                    val applied: ArrayList<Applied_post>) : Parcelable{
    var isFavoue = isFavourite
    var favPost = fav
    var appliedPosts = applied
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
        arrayListOf<Applied_post>().apply {
            parcel.readList(this as ArrayList<*>,Applied_post::class.java.classLoader)
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
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeParcelable(fav, flags)
        if (Build.VERSION.SDK_INT >= 29) {
            parcel.writeParcelableList(applied, flags)
        } else {
            parcel.writeList(applied as List<Applied_post>)
        }
        parcel.writeByte(if (isFavoue) 1 else 0)
        parcel.writeParcelable(favPost, flags)
        if (Build.VERSION.SDK_INT >= 29) {
            parcel.writeParcelableList(appliedPosts, flags)
        } else {
            parcel.writeList(appliedPosts as List<Applied_post>)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Emp_post> {
        override fun createFromParcel(parcel: Parcel): Emp_post {
            return Emp_post(parcel)
        }

        override fun newArray(size: Int): Array<Emp_post?> {
            return arrayOfNulls(size)
        }
    }
        }




