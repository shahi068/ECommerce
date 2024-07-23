package com.adamco.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.adamco.ecommerceapp.data.product_data.SubcategoryResponse
import com.adamco.ecommerceapp.databinding.ActivitySubcategoryBinding
import com.adamco.ecommerceapp.remote.ApiClient
import com.adamco.ecommerceapp.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubcategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubcategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra("category_id")

        if (categoryId != null) {
            fetchSubCategories(categoryId)
        } else {
            Toast.makeText(this, "Category ID is missing.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchSubCategories(categoryId: String) {
        val apiServices: ApiServices = ApiClient.retrofit.create(ApiServices::class.java)
        val call: Call<SubcategoryResponse> = apiServices.getSubCategories(categoryId)

        call.enqueue(object : Callback<SubcategoryResponse> {
            override fun onResponse(call: Call<SubcategoryResponse>, response: Response<SubcategoryResponse>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@SubcategoryActivity, "Failed to load subcategory list. Please retry. Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }

                val subCategoryResponse = response.body()

                if (subCategoryResponse != null) {
                    binding.recyclerView.layoutManager = GridLayoutManager(this@SubcategoryActivity, 2)
                    binding.recyclerView.adapter = SubcategoryAdapter(subCategoryResponse.subcategories)
                }
            }

            override fun onFailure(call: Call<SubcategoryResponse>, t: Throwable) {
                Toast.makeText(this@SubcategoryActivity, "Failed to fetch subcategories: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
