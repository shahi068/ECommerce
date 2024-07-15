package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.data.product_data.CategoryRequestResponse
import com.adamco.ecommerceapp.databinding.ActivityDashboardBinding
import com.adamco.ecommerceapp.remote.ApiClient
import com.adamco.ecommerceapp.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity() {
    private lateinit var binding : ActivityDashboardBinding
    private lateinit var dashAdapter: DashboardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding){
            val userEmail = SharedPreferencesManager.getString(SharedPreferencesManager.USER_EMAIL)

            //API WORK

            val apiServices : ApiServices = ApiClient.retrofit.create(ApiServices::class.java)
            val call : Call<CategoryRequestResponse> = apiServices.getCategories()

            call.enqueue(object : Callback<CategoryRequestResponse>{
                override fun onResponse(call: Call<CategoryRequestResponse>, response: Response<CategoryRequestResponse>
                ) {
                    if(!response.isSuccessful){
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
                    TODO("Not yet implemented")
                }


            })


            // END OF API WORK

            btnLogout.setOnClickListener {
                SharedPreferencesManager.clearAllPref()
                showLogoutDialog()
            }

            btnCart.setOnClickListener {
                val cartIntent = Intent(this@Dashboard, ShoppingCartPage::class.java)
                startActivity(cartIntent)
            }

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

//    private fun getData() = mutableListOf(
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
//        DashboardItem("iPhone", icon = R.drawable.iphone),
//        DashboardItem("Laptop", icon = R.drawable.laptop),
//        DashboardItem("Men's Wear", icon = R.drawable.menswear),
//        DashboardItem("Women's Wear", icon = R.drawable.womenswear))


}