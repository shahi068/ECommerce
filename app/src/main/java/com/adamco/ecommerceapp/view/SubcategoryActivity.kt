package com.adamco.ecommerceapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.adamco.ecommerceapp.databinding.ActivitySubcategoryBinding
import com.adamco.ecommerceapp.model.adapters.SubcategoryAdapter
import com.adamco.ecommerceapp.model.data.product_data.SubcategoryResponse
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubcategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubcategoryBinding
    private lateinit var adapter: SubcategoryAdapter

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
                    val fragments = mutableListOf<Fragment>()
                    val titles = mutableListOf<String>()

                    subCategoryResponse.subcategories.forEach { subcategory ->
                        fragments.add(FragmentOfItems.newInstance(subcategory.subcategory_id))
                        titles.add(subcategory.subcategory_name)
                    }

                    adapter = SubcategoryAdapter(fragments, this@SubcategoryActivity)
                    binding.viewPager.adapter = adapter

                    TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                        tab.text = titles[position]
                    }.attach()
                }
            }

            override fun onFailure(call: Call<SubcategoryResponse>, t: Throwable) {
                Toast.makeText(this@SubcategoryActivity, "Failed to fetch subcategories: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
