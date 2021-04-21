package com.lambton.bikr.firebase

import android.os.Parcel
import android.os.Parcelable

class Restaurant_model( val id: String?,
                        val address: String?,
                        val lat: Double?,
                        val lng: Double?,
                        val name: String?,
                        val image: String?,
                        val url: String?,
val description: String?): Parcelable {
//    val distance: Double = 0.0
    var distance: Double = 0.0

        get() = field

        set(value) {
            field = value
        }
//        get() {
//
//        }
//    // getter
////    get() = field
//
//    // setter
//    set(value) {
//        field = value
//    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(address)
        parcel.writeValue(lat)
        parcel.writeValue(lng)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(url)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant_model> {
        override fun createFromParcel(parcel: Parcel): Restaurant_model {
            return Restaurant_model(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant_model?> {
            return arrayOfNulls(size)
        }
    }

}
