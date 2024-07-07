package com.adamco.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adamco.ecommerceapp.databinding.ActivitySummaryPageBinding

class SummaryPage : AppCompatActivity() {
    private lateinit var binding : ActivitySummaryPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}