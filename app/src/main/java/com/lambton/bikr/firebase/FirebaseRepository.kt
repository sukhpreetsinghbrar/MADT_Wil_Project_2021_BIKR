package com.lambton.bikr.firebase

import FirebaseNetworkCallBack
import User
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lambton.mohitmvvmfirebase.utils.Constants
import com.lambton.mohitmvvmfirebase.utils.Constants.Storage_Base_URL


class FirebaseRepository {


    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDB: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.FIREBASE_DB_URL)
    }
    private val firebaseStroage: StorageReference by lazy {
        FirebaseStorage.getInstance().getReferenceFromUrl(Storage_Base_URL)
    }

    //when User Create The Database is Created
    fun onCreateUser(id: String?, Fname: String?, Lname: String?, profile_pic: String?, user: User,email: String?,callBack: FirebaseNetworkCallBack) {

        // this User model
        val user = User(id!!, Fname!!, Lname!!, profile_pic!!,user.cardNum,user.expMonth,user.expYear,email!!)
        // Create Database on firebase Database
        firebaseDB.child("users").child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //listnerDB?.onDBSuccess("Database Added Successfully")
                callBack.onSuccess("User Is Created")

            } else {
                //listnerDB?.onDBFailure("Data Not Added")
                callBack.onError(task.exception!!.localizedMessage)

            }
        }








        //login
    fun fireBaseLogin(email: String, password: String, callBack: FirebaseNetworkCallBack) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                callBack.onSuccess(it.result?.user!!)
            else {
                callBack.onError(it.exception!!.localizedMessage)
            }
        }
    }

    //create New User
    fun firebaseSignup(email: String, password: String,isJobSeeker : Boolean,gender: String, callBack: FirebaseNetworkCallBack) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callBack.onSuccess(task.result?.user!!)

            } else {
                callBack.onError(task.exception!!.localizedMessage)

            }
        }


    }

    //get instance of current user
    fun currentUser() = firebaseAuth.currentUser

    // Login With Email Password
    fun makeSignOut() {
        firebaseAuth.signOut()
    }


///////////////////////////////////////////////////////// DATABASE ////////////////////////////////////////////////////






///////////////////////////////////////////////////////// STORAGE ////////////////////////////////////////////////////



//    fun uploadtoFirebaseStorge(tag: String,fileUri: Uri?, callBack: FirebaseNetworkCallBack) {
//        // Save the File URI
//
//    }
    fun ff(){
        firebaseDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//            val post = dataSnapshot.getValue(String::class.java)
                //Update the UI with received data

                Log.e("JASSI","CAledddd");
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })

    }


}


    fun getLoggedInUser(s: String, callBack: FirebaseNetworkCallBack) {

        val eventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.child("users").children) {
                    // ds.key("")
                    ds.getValue().toString()
                    //  var pos = ds.getValue(Posts::class.java)

                    //    pos?.name.toString()

                    val map = ds.getValue() as Map<*, *>
                    //   for (mapp in map.entries) {
                    val fname =   ds.child("first_name").getValue(String::class.java)
                    val lname =   ds.child("last_name").getValue(String::class.java)
                    val user_id = ds.child("id").getValue(String::class.java)
                    val user_profile = ds.child("profile_pic").getValue(String::class.java)
                    val cardNum = ds.child("cardNum").getValue(String::class.java)
                    val expMonth = ds.child("expMonth").getValue(String::class.java)
                    val expYear = ds.child("expYear").getValue(String::class.java)
                    val email = ds.child("email").getValue(String::class.java)

                    if(s.equals(user_id) ){
                        val user = User(user_id, fname, lname, user_profile,cardNum,expMonth,expYear,email)


                        //arrayList.add(user)

                        callBack.onSuccess(user)
                        break
                    }

                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                callBack.onError(databaseError.toString())

            }
        }
        firebaseDB.addListenerForSingleValueEvent(eventListener)
    }

    fun updateCardDetails(id: String, user: User, callBack: FirebaseNetworkCallBack) {
        // Create Database on firebase Database
        firebaseDB.child("users").child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //listnerDB?.onDBSuccess("Database Added Successfully")
                callBack.onSuccess("card added")

            } else {
                //listnerDB?.onDBFailure("Data Not Added")
                callBack.onError(task.exception!!.localizedMessage)

            }
        }
    }

    fun uploadtoFirebaseStorge(s: String, mFileUri: Uri, firebaseNetworkCallBack: FirebaseNetworkCallBack) {
        if (mFileUri != null) {

            Log.e("Here 2 ",mFileUri.toString())
            val photoRef =
                    firebaseStroage.child("/Storage").child(mFileUri.lastPathSegment.toString())
            photoRef.putFile(mFileUri).addOnSuccessListener { taskSnapshot ->
                // Upload succeeded

                //    Log.e("Image URL",

                val task =   taskSnapshot.getStorage().getDownloadUrl();
                task.addOnSuccessListener { uri ->
                    val repository = FirebaseRepository()
//https://firebasestorage.googleapis.com/v0/b/newfirebase-2cbe7.appspot.com/o/Storage%2Fimage%3A18?alt=media&token=2e925d39-2e5c-4d31-909b-718827cd1418

                    Log.e("IMAGE", uri.toString())
                    if(s.equals("image")){                  //repository.currentUser()!!.uid
                        firebaseDB.child("users").child("123").child("profile_pic").setValue(uri.toString())

                    }else{
                        //  firebaseDB.child("users").child(repository.currentUser()!!.uid).child("resume_link").setValue(uri.toString())

                    }

                    firebaseNetworkCallBack.onSuccess( uri.toString())//"Image is Uploaded..")
                }


                //    var imgURL = photoRef.downloadUrl.toString()




            }.addOnFailureListener { exception ->
                // Upload failed
                firebaseNetworkCallBack.onError(exception.localizedMessage)
            }
        }

    }

    fun getRestuarentsData(firebaseNetworkCallBack: FirebaseNetworkCallBack) {
            //to fetch all the restuarents of firebase Auth app
            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (ds in dataSnapshot.child("restaurants").children) {
//                        val user_id = ds.child("uid").getValue(String::class.java)
//
                        // if (repository.currentUser()!!.uid.equals(user_id)) {


//                            val jobTit = ds.child("jobTitle")
//                                .getValue(String::class.java)// ds.child("name").getValue(String::class.java)
                        //    val user_id = ds.child(mapp.key as String).child("id").getValue(String::class.java)
                        val id = ds.child("id").getValue(String::class.java)

                        val description = ds.child("description").getValue(String::class.java)
                        val name = ds.child("name").getValue(String::class.java)
                        val image = ds.child("image").getValue(String::class.java)
                        val lat = ds.child("lat").getValue(Double::class.java)
                        val lng = ds.child("lng").getValue(Double::class.java)
                        val address = ds.child("address").getValue(String::class.java)
                        val url = ds.child("url").getValue(String::class.java)

                        val rest = Restaurant_model(
                            id,
                            address,
                            lat,
                            lng,
                            name,
                            image,
                            url,description
                        )

                        //  val arrayList = ArrayList<User>()

                        //arrayList.add(user)

                        firebaseNetworkCallBack.onSuccess(rest)
                        // }
                        //  }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    firebaseNetworkCallBack.onError(databaseError.toString())

                }
            }
            firebaseDB.addListenerForSingleValueEvent(eventListener)
        }


}