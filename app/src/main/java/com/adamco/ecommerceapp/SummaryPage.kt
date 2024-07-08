package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.databinding.ActivitySummaryPageBinding

class SummaryPage : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var summaryPageAdapter: SummaryPageAdapter
    private lateinit var binding : ActivitySummaryPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        initRecyclerView()
        updateTotalPrice()
        initViews()
    }

    private fun initViews() {
        with(binding){
            btnConfirm.setOnClickListener {
                val nextIntent = Intent(this@SummaryPage, DeliveryPage::class.java)
                startActivity(nextIntent)
            }
        }
    }

    private fun initRecyclerView() {
        val itemList = databaseHelper.readData().toMutableList()
        summaryPageAdapter = SummaryPageAdapter(itemList, databaseHelper) { totalBill ->
            updateTotalPrice(totalBill)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = summaryPageAdapter
    }

    private fun updateTotalPrice(totalBill: Double? = null) {
        val total = totalBill ?: calculateTotalBill()
        binding.calcTotalBill.text = "$${total}"
    }

    private fun calculateTotalBill(): Double {
        val itemList = databaseHelper.readData()
        var total = 0.0
        for (item in itemList) {
            total += item.itemPrice * item.itemQuantity
        }
        return total
    }
}