package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adamco.ecommerceapp.databinding.ActivityDeliveryPageBinding

class DeliveryPage : AppCompatActivity() {
    private lateinit var binding : ActivityDeliveryPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding){
            btnNext.setOnClickListener{
                val nextIntent = Intent(this@DeliveryPage, PaymentPage::class.java)
                startActivity(nextIntent)
            }
        }
    }
}