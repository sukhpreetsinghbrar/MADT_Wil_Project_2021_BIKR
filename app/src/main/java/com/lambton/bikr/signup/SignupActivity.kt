

package com.lambton.bikr.signup
import BaseActivity
import User
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambton.bikr.R
import com.lambton.bikr.databinding.ActivitySignupBinding
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : BaseActivity() {


    private var mFileUri: Uri? = null
    var binding: ActivitySignupBinding? = null
    var user = User()
    var REQUST_CAMERA = 101
    var edit_text_date: EditText? = null
   var rg : RadioGroup? = null

    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        rg = findViewById(R.id.rg) as RadioGroup
        edit_text_date = findViewById(R.id.edit_text_date) as EditText
        binding?.viewmodel = viewModel

//        viewModel.muserCreated.observe(this, Observer {
//            startHomeActivity()
//        })

        viewModel.apiLoader.observe(this, Observer {
            if (it) {
                showProcessDialog(this, "User Is Creating..")
            } else {
                hideProcessDialog()
            }
        })

        viewModel.image_pick_picker.observe(this, Observer {
            if (it) {
                image_picker()
            }
        })

        rg!!.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })
        //binding.setUser(new User("Princess Diana", "USA"));
        //binding?.imageUrl = "https://androidwave.com/wp-content/uploads/2019/01/profile_pic.jpg"
        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MMM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            edit_text_date?.setText(sdf.format(cal.time))

        }

        edit_text_date!!.setOnClickListener {
            DatePickerDialog(this@SignupActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }


    //     set onclick in xml for pick image
    fun image_picker() {
        // Pick an image from storage
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUST_CAMERA)

    }

    //
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Log.d(TAG, "onActivityResult:$requestCode:$resultCode:$data")
        if (requestCode == REQUST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                mFileUri = data?.data
                if (mFileUri != null) {

                    Glide.with(this)
                        .load(mFileUri) // Uri of the picture
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding!!.userProfilePhoto)//binding?.userProfilePhoto

                    //send file Uri to viewModel
                    viewModel.mFile_viewModel=mFileUri

                } else {
                    Log.w(ContentValues.TAG, "File URI is null")
                }
            } else {
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
