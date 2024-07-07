package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adamco.ecommerceapp.databinding.ActivityPaymentPageBinding

class PaymentPage : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding){
            btnNext.setOnClickListener{
                val nextIntent = Intent(this@PaymentPage, SummaryPage::class.java)
                startActivity(nextIntent)
            }
        }
    }
}