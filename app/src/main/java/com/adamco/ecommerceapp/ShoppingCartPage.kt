package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.databinding.ActivityShoppingCartPageBinding

class ShoppingCartPage : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartPageBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var shoppingCartAdapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        initRecyclerView()
        updateTotalPrice()
        initViews()
    }

    private fun initViews() {
        with(binding){
            btnCheckout.setOnClickListener {
                val intent = Intent(this@ShoppingCartPage, CheckingOutPage::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        val itemList = databaseHelper.readData().toMutableList()
        shoppingCartAdapter = ShoppingCartAdapter(itemList, databaseHelper) { totalBill ->
            updateTotalPrice(totalBill)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = shoppingCartAdapter
    }

    private fun updateTotalPrice(totalBill: Double? = null) {
        val total = totalBill ?: calculateTotalBill()
        binding.calcTotalBill.text = "$${total}"
    }

    private fun calculateTotalBill(): Double {
        val itemList = databaseHelper.readData()
        var total = 0.00
        for (item in itemList) {
            total += (item.itemQuantity * item.itemPrice)
        }
        return total
    }
}
