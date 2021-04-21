package com.lambton.bikr.activity

import FirebaseNetworkCallBack
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mohitmvvmfirebase.ui.activity.emp_posts.RestuarntRecyclerAdapter
import com.lambton.bikr.R
import com.lambton.bikr.firebase.FirebaseRepository
import com.lambton.bikr.firebase.Restaurant_model
import kotlin.collections.ArrayList

class RestaurentsActivity : AppCompatActivity() {

    var  adapter1: RestuarntRecyclerAdapter? = null
    val repository = FirebaseRepository()
    var currentLat: Double = 0.0
    var currentLong: Double = 0.0
    lateinit var restuarant: Restaurant_model
    var arrayList = ArrayList<Restaurant_model>()
    lateinit var list_recycler_view: RecyclerView
// //   var dup_arrayList = ArrayList<Emp_post>()
//    private lateinit var viewModel: UserListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurents)
    list_recycler_view = findViewById(R.id.list_recycler_view)
//    setSupportActionBar(toolbar)
    supportActionBar?.title = "Nearest Restaurants"
    currentLat = intent.getDoubleExtra("current_lat",0.0)
    currentLong = intent.getDoubleExtra("current_long",0.0)
    supportActionBar?.setDisplayHomeAsUpEnabled(true);
    supportActionBar?.setDisplayShowHomeEnabled(true);

    arrayList.clear()
//        dup_arrayList.clear()
    repository.getRestuarentsData(object : FirebaseNetworkCallBack{
        override fun onSuccess(response: Any) {
            //  dup_arrayList.add(response as Restaurant_model)
            restuarant = response as Restaurant_model
            Log.e("distancRE2=",  distance(currentLat,currentLong,restuarant.lat!!,restuarant.lng!!).toString())
            Log.e("Rounded== ",String.format("%.0f", distance(currentLat,currentLong,restuarant.lat!!,restuarant.lng!!)))

            Log.e("milestokm== ",String.format("%.3f", distance(currentLat,currentLong,restuarant.lat!!,restuarant.lng!!)))
            var miles = String.format("%.3f", distance(currentLat,currentLong,restuarant.lat!!,restuarant.lng!!)).toDouble()
            var   milestoKm = String.format("%.1f", miles * 1.609)

            val arr = milestoKm.toString().split("\\.".toRegex()).toTypedArray()
      val c = arr[0].toInt() // 1
                val cost = c + 1

            restuarant.distance = cost.toDouble()




            arrayList.add(restuarant)



//                Collections.sort(arrayList, Comparator<Any?> { obj1, obj2 ->
//                    // ## Ascending order
//                    obj1.distance.compare(obj2.distance) // To compare string values
//                    // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
//
//                    // ## Descending order
//                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
//                    // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
//                })
            //getting recyclerview from xml



            list_recycler_view.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                //    layoutManager = LinearLayoutManager(this@EmpPostsActivity)
                // set the custom adapter to the RecyclerView
                arrayList.sortBy { it.distance }
                layoutManager = LinearLayoutManager(context)
                //  arrayList.reverse()
                //   dup_arrayList.reverse()
                adapter1 = RestuarntRecyclerAdapter(arrayList,context, this@RestaurentsActivity,currentLat,currentLong)
                //AdapterList(this@UserListActivity,dup_arrayList, this@UserListActivity)
                adapter = adapter1
            }

        }

        override fun onError(excecption: String) {
            // toast(excecption)
        }
    })
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }
    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}
