package com.lambton.bikr
import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.lambton.bikr.activity.HomeActivity
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 5000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//methodWithPermissions()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, HomeActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
//    fun methodWithPermissions() = runWithPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION) {
//        Toast.makeText(this, "Location permissions granted", Toast.LENGTH_SHORT).show();
//        // Do the stuff with permissions safely
//    }
}