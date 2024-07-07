package com.adamco.ecommerceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.databinding.ActivityItemsFromCategoryBinding

class ItemsFromCategory : AppCompatActivity() {
    private lateinit var binding: ActivityItemsFromCategoryBinding
    private lateinit var categoryAdapter: ItemCategoryAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsFromCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@ItemsFromCategory)
            categoryAdapter = ItemCategoryAdapter(this@ItemsFromCategory, getData(), databaseHelper)
            recyclerView.adapter = categoryAdapter
        }
    }

    private fun getData() = mutableListOf(
        ItemCategoryDetails("iPhone", "Amazing iPhone buy now!", 5, R.drawable.iphone),
        ItemCategoryDetails("Menswear", "Amazing menswear buy now!", 5, R.drawable.menswear),
        ItemCategoryDetails("Clothes", "Amazing clothes buy now!", 5, R.drawable.womenswear),
        ItemCategoryDetails("Laptop", "Amazing laptop buy now!", 5, R.drawable.laptop)
    )
}
