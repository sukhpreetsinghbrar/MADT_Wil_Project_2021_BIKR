package com.lambton.bikr.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lambton.bikr.R
import com.lambton.bikr.firebase.Restaurant_model

class DelievryDetailActivity : AppCompatActivity() {
    var restuarent : Restaurant_model? = null
   lateinit var tvname:TextView
    lateinit var tv_map:TextView
    var currentLat: Double = 0.0
    var currentLong: Double = 0.0
    lateinit var tv_dis:TextView
    lateinit var tvPrice: TextView
    lateinit var tvConfirm: TextView
   var  distance:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delievry_detail)
        supportActionBar?.title = "Delivery Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        restuarent = intent.getParcelableExtra<Restaurant_model>("rest_data")
        distance = intent.getDoubleExtra("distance",0.0)

        currentLat = intent.getDoubleExtra("current_lat",0.0)
        currentLong = intent.getDoubleExtra("current_long",0.0)

//        val arr = distance.toString().split("\\.".toRegex()).toTypedArray()
//      val c = arr[0].toInt() // 1
//                val cost = c + 1


      //  var   cost = String.format("%.0f", distance)
        tvname = findViewById(R.id.tvname)
        tvPrice = findViewById(R.id.tvPrice)
        tvPrice.text = "$"+distance.toString()
        tv_dis = findViewById(R.id.tv_dis)
        tv_dis.text= distance.toString()+" Km"
        tvConfirm =  findViewById(R.id.tvConfirm)
        tv_map =  findViewById(R.id.tv_map)

        tvname = findViewById(R.id.tvname)
        tvname.text = restuarent?.name
        tvConfirm.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Confirmed.....", Toast.LENGTH_SHORT).show()
            finish()
        })
        tv_map.setOnClickListener(View.OnClickListener {
          //  Toast.makeText(this,"Confirmed.....", Toast.LENGTH_SHORT).show()
            var i = Intent(this, MapsActivity::class.java)
            i.putExtra("current_lat",currentLat)
            i.putExtra("current_long",currentLong)
            i.putExtra("dest_lat",restuarent!!.lat)
            i.putExtra("dest_long",restuarent!!.lng)
            i.putExtra("dest_name",restuarent!!.name)
//                i.putParcelableArrayListExtra("responders_list",user.appliedPosts)
//                //         it.putExtra("userid",sender_id)
////                    ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                listenerContact.startActivity(i)
            startActivity(i)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}