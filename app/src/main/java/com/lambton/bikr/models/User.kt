
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude


/// in kotlin model class is used to store the data

data class User(
    var id: String? = "",
    var first_name: String? = "",
    var last_name: String? = "",
    var profile_pic: String? = "",
    var cardNum: String? = "",
    var expMonth: String? = "",
    var expYear: String? = "",

   var email: String? = ""
//var gender: String? = "",
//    var dob: String? = "",

): Parcelable {


    // toMap is used to Store Multipale data in List
    // when we add or get data we use tomap

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("id", id!!)
        result.put("first_name", first_name!!)
        result.put("last_name", last_name!!)
        result.put("profile_pic", profile_pic!!)
        result.put("cardNum", cardNum!!)
        result.put("expMonth", expMonth!!)
        result.put("expYear", expYear!!)
        result.put("email", email!!)
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(profile_pic)
        parcel.writeString(cardNum)
        parcel.writeString(expMonth)
        parcel.writeString(expYear)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}


