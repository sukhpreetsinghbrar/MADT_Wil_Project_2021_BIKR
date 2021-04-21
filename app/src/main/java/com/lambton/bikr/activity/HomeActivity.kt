package com.lambton.bikr.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.lambton.bikr.R
import com.lambton.bikr.fragments.HomeFragment
import com.lambton.bikr.fragments.PaymentFragment
import com.lambton.bikr.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var txtProfileName: TextView

//    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var previousMenuItem: MenuItem? = null

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)

methodWithPermissions()

//        sharedPreferences =
//            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
//        val name: String? = sharedPreferences.getString("name", "user@12345")
        val headerView = navigationView.getHeaderView(0)
        txtProfileName = headerView.findViewById(R.id.txtProfileName)
        txtProfileName.text = "Hello, User"//$name"

        setToolbar()
        openHome()

        val actionBarToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it
            when (it.itemId) {
                R.id.menuHome -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.menuProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ProfileFragment()
                        ).commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.menuPay -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            PaymentFragment()
                        ).commit()
                    supportActionBar?.title = "Payment"
                    drawerLayout.closeDrawers()
                }
//                }
                R.id.menuLogout -> {
                    val dialog = AlertDialog.Builder(this@HomeActivity)
                    dialog.setTitle("Exit!!")
                    dialog.setMessage("Do you wish to continue?")
                    dialog.setPositiveButton("Exit") { _, _ ->
//                        sharedPreferences.edit().clear().apply()
//                        val intent = Intent(this@HomeActivity, FireBaseLoginActivity::class.java)
//                        startActivity(intent)
                        this@HomeActivity.finish()
                    }
                    dialog.setNegativeButton("Cancel") { _, _ ->
                    }
                  //  dialog.create()
                    dialog.show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun methodWithPermissions() = runWithPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION) {
        Toast.makeText(this, "Location permissions granted", Toast.LENGTH_SHORT).show();
    }
    private fun openHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Home"
        navigationView.setCheckedItem(R.id.menuHome)
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.frame)) {
            !is HomeFragment -> openHome()
            else -> super.onBackPressed()
        }
    }
}
