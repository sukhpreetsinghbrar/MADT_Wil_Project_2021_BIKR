package com.lambton.bikr.fragments

import FirebaseNetworkCallBack
import User
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambton.bikr.R
import com.lambton.bikr.firebase.FirebaseRepository
import com.lambton.bikr.utils.MyCustomDialog
import com.mikhaellopez.circularimageview.CircularImageView
import java.io.File


class ProfileFragment : Fragment(), MyCustomDialog.dialogViewCallBack {

    lateinit var baseprogress: ProgressDialog
    private lateinit var filePhoto: File
    private var mFileUri: Uri? = null
    lateinit var user_profile_photo: CircularImageView
    var REQUST_CAMERA = 101
   var REQUEST_CODE = 13
    var isNew = true
    val apiLoader = MutableLiveData<Boolean>()
    lateinit var  itemEd: MenuItem
    lateinit var  item1: MenuItem
lateinit var et_fn: AppCompatEditText
    lateinit var et_ln: AppCompatEditText

    lateinit var profile_email: AppCompatEditText
    val repository = FirebaseRepository()
    lateinit var  user: User
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        user_profile_photo = view.findViewById(R.id.user_profile_photo)
        et_fn = view.findViewById(R.id.et_fn)
        et_ln = view.findViewById(R.id.et_ln)
        profile_email = view.findViewById(R.id.profile_email)

        user_profile_photo.setOnClickListener(View.OnClickListener {
//            MyCustomDialog().context

            val myCustomDialog: MyCustomDialog? = MyCustomDialog().myCustomDialog(this)
            myCustomDialog!!.show(childFragmentManager, "MyCustomFragment")


        })

        activity?.let {
            apiLoader.observe(it, Observer {
                if (it) {

                Toast.makeText(context,"updating.....", Toast.LENGTH_SHORT).show()
                   // showProcessDialog(this, "User Is Creating..")
                } else {
                //    hideProcessDialog()
                }
            })
        }


        repository.getLoggedInUser("123",object: FirebaseNetworkCallBack {
            override fun onSuccess(response: Any) {
                user = response as User

                et_fn!!.setText(user.first_name)
                et_ln!!.setText(user.last_name)
                profile_email!!.setText(user.email)
//                val myOptions = RequestOptions()
                Log.e("img ccccc",user.profile_pic!!);
//                    .override(100, 100)
                Glide.with(context!!)
                    .load(user.profile_pic)
                    .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_uservbv).error(
                        R.drawable.ic_uservbv))
                    .into(user_profile_photo!!)//binding?.userProfilePhoto


            }

            override fun onError(excecption: String) {

                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }})




        return view
    }
    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }
    fun image_picker() {
        // Pick an image from storage
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUST_CAMERA)

    }
    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
         itemEd = menu.findItem(R.id.action_edit)
         item1 = menu.findItem(R.id.action_done)
        et_fn.isEnabled = false
        et_ln.isEnabled = false
        profile_email.isEnabled = false
        user_profile_photo.isEnabled = false
        if(isNew){
            itemEd.setVisible(true);
            item1.setVisible(false)
        }else{
            itemEd.setVisible(false);
            item1.setVisible(true)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_edit) {
//            tv_fn
//
            et_fn.isEnabled = true
            profile_email.isEnabled = true
            user_profile_photo.isEnabled = true
            et_ln.isEnabled = true
            itemEd.setVisible(false);
            item1.setVisible(true)
        }
        if(id == R.id.action_done){
            if (et_fn.text.toString().isNullOrEmpty() || et_ln.text.toString().isNullOrEmpty() || profile_email.text.toString().isNullOrEmpty()) {
                Toast.makeText(context,"First Name or Last name or Email can not be empty", Toast.LENGTH_SHORT).show()

            }else{

            createDBuser("123",et_fn.text.toString(),et_ln.text.toString(),user.profile_pic!!,user!!,profile_email.text.toString())//"profile_pic")
            et_fn.isEnabled = false
            et_ln.isEnabled = false
                profile_email.isEnabled = false
                user_profile_photo.isEnabled = false
            item1.setVisible(false)
            itemEd.setVisible(true);
                }

        }
//        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Log.d(TAG, "onActivityResult:$requestCode:$resultCode:$data")
        if (requestCode == REQUST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                mFileUri = data?.data
                if (mFileUri != null) {

                    //  uploadData(mFileUri!!,"image")
                    Glide.with(this)
                        .load(mFileUri) // Uri of the picture
                        .apply(RequestOptions.circleCropTransform())
                        .into(user_profile_photo!!)//binding?.userProfilePhoto

                    //send file Uri to viewModel
                    //   viewModel.mFile_viewModel=mFileUri

                } else {
                    Log.w(ContentValues.TAG, "File URI is null")
                }
            }
        }

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mFileUri = Uri.fromFile(filePhoto);
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
           // user_profile_photo.setImageBitmap(takenPhoto)
            Glide.with(this)
                .load(mFileUri) // Uri of the picture
                .apply(RequestOptions.circleCropTransform())
                .into(user_profile_photo!!)//
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

//    override fun onViewLoaded(textView: TextView?) {
//        TODO("Not yet implemented")
//    }

    override fun onViewLoaded(textView: TextView?) {
        Log.e("Reachedd","-------")
        Log.e("Reachedd n",textView.toString())
        if(textView!!.id == R.id.tv_cam) {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            filePhoto = getPhotoFile("photo.jpg")


            val providerFile =FileProvider.getUriForFile(activity!!,"com.lambton.bikr", filePhoto)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
            if (takePhotoIntent.resolveActivity(activity!!.packageManager) != null){
                startActivityForResult(takePhotoIntent, REQUEST_CODE)
            }else {
//                Toast.makeText(context,"Camera could not open", Toast.LENGTH_SHORT).show()
                Log.e("Reachedd","failed")
//                Toast.makeText(activity!!, "Camera could not open", Toast.LENGTH_SHORT).show()
            }
        }
        if(textView!!.id == R.id.tv_gallery){
            image_picker()

        }
    }

    private fun createDBuser(uid: String, Fname: String, Lname: String, profile_pic: String, user: User,email: String) {

        repository.onCreateUser(uid, Fname, Lname, profile_pic,user,email, object : FirebaseNetworkCallBack {
            override fun onSuccess(response: Any) {

                apiLoader.value = false

                if(mFileUri!=null){
                    baseprogress = ProgressDialog(context).apply {
                        setTitle("Loading..")
                        setCancelable(false)
                        setCanceledOnTouchOutside(false)
                        show()}
                    Log.e("Here 1 ",mFileUri.toString())
                    repository.uploadtoFirebaseStorge("image", mFileUri!!,object: FirebaseNetworkCallBack{
                        override fun onSuccess(response: Any) {
                          apiLoader.value = true
                            baseprogress.dismiss()
                        }

                        override fun onError(excecption: String) {
//                            apiLoader.value = false
                            baseprogress.dismiss()
//                            mGeneralError.value = excecption
                        }

                    })
                }}

            override fun onError(excecption: String) {
//                mGeneralError.value = excecption
               apiLoader.value = false

            }
        })
    }


}




