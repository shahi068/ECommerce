package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.databinding.ActivityDashboardBinding

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


            recyclerView.layoutManager = GridLayoutManager(this@Dashboard, 2)
            recyclerView.adapter = DashboardAdapter(getData())
            dashAdapter = DashboardAdapter(getData())

            btnLogout.setOnClickListener {
                SharedPreferencesManager.clearAllPref()
                val signedOutIntent = Intent(this@Dashboard, SignInActivity::class.java)
                startActivity(signedOutIntent)
            }

            btnCart.setOnClickListener {
                val cartIntent = Intent(this@Dashboard, ShoppingCartPage::class.java)
                startActivity(cartIntent)
            }

        }

    }

    private fun getData() = mutableListOf(
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear),
        DashboardItem("iPhone", icon = R.drawable.iphone),
        DashboardItem("Laptop", icon = R.drawable.laptop),
        DashboardItem("Men's Wear", icon = R.drawable.menswear),
        DashboardItem("Women's Wear", icon = R.drawable.womenswear))


}