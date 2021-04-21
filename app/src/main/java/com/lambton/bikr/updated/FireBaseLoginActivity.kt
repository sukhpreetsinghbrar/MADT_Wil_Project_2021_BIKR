package com.lambton.mohitmvvmfirebase.updated

import BaseActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambton.bikr.R
import com.lambton.bikr.databinding.FirebaseLoginBinding
import startNearByActivity
import startSignupActivity
import toast


class FireBaseLoginActivity : BaseActivity() {

    private lateinit var viewModel: FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: FirebaseLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.firebase_login)

        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
        binding.viewmodel = viewModel

    //    val intent = Intent(Intent.ACTION_VIEW)
     //   var pdf = "https://firebasestorage.googleapis.com/v0/b/newfirebase-2cbe7.appspot.com/o/Storage%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FJASWINDER_Resume.pdf?alt=media&token=1a585c0e-2e03-4dc0-b3f0-09475df1b1fd"
   //     intent.setType ("application/*")
       // intent.data = Uri.parse("https://firebasestorage.googleapis.com/v0/b/newfirebase-2cbe7.appspot.com/o/Storage%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FJASWINDER_Resume.pdf?alt=media&token=1a585c0e-2e03-4dc0-b3f0-09475df1b1fd")
   // intent.data = Uri.parse("https://firebasestorage.googleapis.com/v0/b/newfirebase-2cbe7.appspot.com/o/Storage%2F1486?alt=media&token=d0832fcf-6e32-429f-bcd3-105e7a166af0")
   //     startActivity(intent)
        observeData()
        var doc = "<iframe src='http://docs.google.com/viewer?url=http://www.iasted.org/conferences/formatting/"+
                "presentations-tips.ppt&embedded=true'width='100%' height='100%' style='border: none;'></iframe>";
//        val wv = findViewById(com.example.mohitmvvmfirebase.R.id.webView1) as WebView
//        wv.settings.javaScriptEnabled = true
//      //  wv.settings.setPluginsEnabled(true)
//        wv.settings.allowFileAccess = true
//        wv.loadUrl(doc)

//        wv.getSettings().setJavaScriptEnabled(true);
//     //   wv.getSettings().pluginState = true//(true);
//        wv.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf);
//        setContentView(wv);

    }

    private fun observeData() {

        //observe data
        viewModel.firebaseUser.observe(this, Observer {
            it?.let {
                toast(it.email)
              //  startHomeActivity()
            }
        })
        viewModel.validationError.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
              //  toast(it)
                startNearByActivity()
            }
        })
        viewModel.loader.observe(this, Observer {
            if (it) {
                showProcessDialog(this, "Loading..")
            } else {
                hideProcessDialog()
            }
        })
        viewModel.gotoSign_up.observe(this, Observer {
            if (it) {
                startSignupActivity()
            }
        })
    }


}

