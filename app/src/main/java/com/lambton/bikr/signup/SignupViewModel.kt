
package com.lambton.bikr.signup

import FirebaseNetworkCallBack
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.lambton.bikr.firebase.FirebaseRepository


class SignupViewModel : ViewModel() {

    //email and password for the input
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var type: Int? = null
    var mFile_viewModel: Uri? = null
var emp: Boolean = false
    var seek: Boolean = false
    var JobSeek: Boolean = false
    var rb1: Boolean = false
    var rb2: Boolean = false
    var rb3: Boolean = false
    var gender: String? = null
    var genderID: Int? = null
    var dob: String? = null
    var city: String? = null
    var zipcode: String? = null
    val apiLoader = MutableLiveData<Boolean>()
    val mFirebaseUser = MutableLiveData<FirebaseUser>()
    val mGeneralError = MutableLiveData<String>()
    val repository = FirebaseRepository()
    val muserCreated = MutableLiveData<String>()
    var image_pick_picker = MutableLiveData<Boolean>()


    val user by lazy {
      //  repository.currentUser()
    }


    fun goToLogin(view: View) {

//        Intent(view.context, FireBaseLoginActivity::class.java).also {
//
//            view.context.startActivity(it)

//        }
    }
//

    // perform to signup


    fun signup() {
        apiLoader.value = true
Log.e("gende val",type.toString())
        //2131230939
      if(emp){
          JobSeek = false
      }else if(seek){
          JobSeek = true
      }

       if(rb1){
           gender = "Male"
       }else if(rb2 ){
           gender = "Female"
       }else{
           gender = "Other"
       }
//
        //2131230941

        //validating email and password
        if (name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            mGeneralError.postValue("Invalid email or password")
            apiLoader.value = false
            return
        }

//        repository.firebaseSignup(email!!, password!!,JobSeek,gender!!, object :
//            FirebaseNetworkCallBack {
//
//            override fun onSuccess(response: Any) {
//                //here callback user returns FirebaseUser
//                mFirebaseUser.value = response as FirebaseUser
//
//                val name = Utility.usernameFromEmail(response.email.toString())
//
//              //  createDBuser(response.uid, name, response.email!!, "profile when user create",JobSeek,gender!!,dob!!,"","")
//            }
//
//            override fun onError(excecption: String) {
//                mGeneralError.value = excecption
//            }
//        })
    }

//    private fun createDBuser(uid: String, name: String, email: String, profile_pic: String, jobSeeker: Boolean,gender: String,dob: String,city: String,zipcode: String) {
//
//        repository.onCreateUser(uid, name, email, profile_pic,jobSeeker,gender,dob,city,zipcode, object : FirebaseNetworkCallBack {
//            override fun onSuccess(response: Any) {
//
//                //here callback user returns FirebaseUser
//                muserCreated.value = response as String
//                //after this observe start new activity
//                apiLoader.value = false
//
//if(mFile_viewModel!=null){
//                repository.UploadtoFirebaseStorage("image",mFile_viewModel,object:
//                    FirebaseNetworkCallBack {
//                    override fun onSuccess(response: Any) {
//                        apiLoader.value = false
//                    }
//
//                    override fun onError(excecption: String) {
//                        apiLoader.value = false
//                        mGeneralError.value = excecption
//                    }
//
//                })
//            }}
//
//            override fun onError(excecption: String) {
//                mGeneralError.value = excecption
//                apiLoader.value = false
//
//            }
//        })
//    }

    fun image_picker() {
        image_pick_picker.value = true
    }
}