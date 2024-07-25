package com.adamco.ecommerceapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.ActivityCheckingOutPageBinding

class CheckingOutPage : AppCompatActivity() {
    private lateinit var binding: ActivityCheckingOutPageBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var checkingoutAdapter: com.adamco.ecommerceapp.model.adapters.CheckingOutPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckingOutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        initRecyclerView()
        updateTotalPrice() // Initial total price calculation
        initViews()
    }

    private fun initViews() {
        with(binding){
            btnNext.setOnClickListener {
                val nextIntent = Intent(this@CheckingOutPage, DeliveryPage::class.java)
                startActivity(nextIntent)
            }
        }
    }

    private fun initRecyclerView() {
        val itemList = databaseHelper.readData().toMutableList()
        checkingoutAdapter = com.adamco.ecommerceapp.model.adapters.CheckingOutPageAdapter(
            itemList,
            databaseHelper
        ) { totalBill ->
            updateTotalPrice(totalBill)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = checkingoutAdapter
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
