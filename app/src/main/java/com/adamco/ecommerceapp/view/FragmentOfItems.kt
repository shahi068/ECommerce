package com.adamco.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adamco.ecommerceapp.SharedPreferencesManager
import com.adamco.ecommerceapp.databinding.FragmentOfItemsBinding
import com.adamco.ecommerceapp.model.adapters.FragmentOfItemsAdapter
import com.adamco.ecommerceapp.model.data.items_from_subcategory_data.SubcategoryItemResponse
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.adamco.ecommerceapp.DatabaseHelper

class FragmentOfItems : Fragment() {
    private lateinit var binding: FragmentOfItemsBinding
    private var subCategoryId: String? = null
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCategoryId = it.getString("subcategory_id")
        }
        // Initialize the DatabaseHelper
        databaseHelper = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfItemsBinding.inflate(inflater, container, false)

        SharedPreferencesManager.init(requireContext())

        getItemsFromSubcategory()
        return binding.root
    }

    private fun getItemsFromSubcategory() {
        val apiService: ApiServices = ApiClient.retrofit.create(ApiServices::class.java)
        val call: Call<SubcategoryItemResponse> = apiService.getItemsFromSubcategories(subCategoryId!!)

        call.enqueue(object : Callback<SubcategoryItemResponse> {
            override fun onResponse(call: Call<SubcategoryItemResponse>, response: Response<SubcategoryItemResponse>) {
                Log.d("FragmentOfItems", "Response Code: ${response.code()}")
                Log.d("FragmentOfItems", "Response Body: ${response.body()}")

                if (!response.isSuccessful) {
                    Toast.makeText(context, "Failure in fetching subcategory items: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    Log.e("FragmentOfItems", "Failure in fetching subcategory items: ${response.errorBody()?.string()}")
                    return
                }

                val responseBody = response.body()

                if (responseBody == null) {
                    Toast.makeText(context, "Empty response from the server", Toast.LENGTH_LONG).show()
                    Log.e("FragmentOfItems", "Empty response from the server")
                    return
                }

                val adapter = FragmentOfItemsAdapter(responseBody.products, databaseHelper)
                binding.recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<SubcategoryItemResponse>, t: Throwable) {
                Log.e("FragmentOfItems", "Error fetching subcategory items", t)
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(subCategoryId: String) =
            FragmentOfItems().apply {
                arguments = Bundle().apply {
                    putString("subcategory_id", subCategoryId)
                }
            }
    }
}

