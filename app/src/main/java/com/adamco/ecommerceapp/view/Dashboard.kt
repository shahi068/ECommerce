package com.adamco.ecommerceapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.adamco.ecommerceapp.R
import com.adamco.ecommerceapp.SharedPreferencesManager
import com.adamco.ecommerceapp.databinding.ActivityDashboardBinding
import com.adamco.ecommerceapp.model.adapters.DashboardAdapter
import com.adamco.ecommerceapp.model.data.product_data.CategoryRequestResponse
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashAdapter: DashboardAdapter
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Initialize SharedPreferences
        SharedPreferencesManager.init(this)

        initViews()
    }

    private fun initViews() {
        drawerLayout = binding.main
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }

        binding.navView.setNavigationItemSelectedListener(this)

        with(binding) {
            val userEmail = SharedPreferencesManager.getString(SharedPreferencesManager.USER_EMAIL)

            // API WORK
            val apiServices: ApiServices = ApiClient.retrofit.create(ApiServices::class.java)
            val call: Call<CategoryRequestResponse> = apiServices.getCategories()

            call.enqueue(object : Callback<CategoryRequestResponse> {
                override fun onResponse(call: Call<CategoryRequestResponse>, response: Response<CategoryRequestResponse>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@Dashboard,
                            "Failed to load category list. Please retry. error code: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }

                    val categoryResponse = response.body()

                    recyclerView.layoutManager = GridLayoutManager(this@Dashboard, 2)
                    if (categoryResponse != null) {
                        recyclerView.adapter = DashboardAdapter(categoryResponse.categories)
                    }
                }

                override fun onFailure(call: Call<CategoryRequestResponse>, thrwble: Throwable) {
                    Toast.makeText(
                        this@Dashboard,
                        "Error fetching categories: ${thrwble.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            // END OF API WORK
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ ->
                finish()
            }
            setNegativeButton("No, go back.") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> toastCreator("Home selected")
            R.id.nav_cart -> {
                val cartIntent = Intent(this, ShoppingCartPage::class.java)
                startActivity(cartIntent)
            }
            R.id.nav_orders -> toastCreator("Orders selected")
            R.id.nav_profile -> toastCreator("Profile selected")
            R.id.nav_logout -> {
                SharedPreferencesManager.clearAllPref()
                showLogoutDialog()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
            R.id.action_search -> {
                // Handle search icon click
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toastCreator(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
