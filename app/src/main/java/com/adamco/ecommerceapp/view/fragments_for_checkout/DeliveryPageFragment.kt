package com.adamco.ecommerceapp.view.fragments_for_checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.R
import com.adamco.ecommerceapp.SharedPreferencesManager
import com.adamco.ecommerceapp.databinding.AddressDialogboxBinding
import com.adamco.ecommerceapp.databinding.FragmentDeliveryPageBinding
import com.adamco.ecommerceapp.model.adapters.DeliveryPageAdapter
import com.adamco.ecommerceapp.model.data.delivery_data.AddAddressRequest
import com.adamco.ecommerceapp.model.data.delivery_data.AddAddressResponse
import com.adamco.ecommerceapp.model.data.delivery_data.Address
import com.adamco.ecommerceapp.model.data.delivery_data.GetAddressesResponse
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryPageFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryPageBinding
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var locationName: String
    lateinit var fullAddress: String
    val apiServices = ApiClient.retrofit.create(ApiServices::class.java)

    private lateinit var apiRequest: AddAddressRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryPageBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        initViews()
        return binding.root
    }

    private fun initViews() {
        getAddresses()
        binding.btnAddAddress.setOnClickListener {
            showAddressDialog()
        }
    }

    private fun getAddresses() {
        val userID = SharedPreferencesManager.getString(SharedPreferencesManager.USER_ID).toInt()
        val call: Call<GetAddressesResponse> = apiServices.getAddresses(userID)

        call.enqueue(object : Callback<GetAddressesResponse> {
            override fun onResponse(call: Call<GetAddressesResponse>, response: Response<GetAddressesResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Addresses retrieved successfully", Toast.LENGTH_LONG).show()

                    val responseBody = response.body()?.addresses

                    if (responseBody != null) {
                        initAdapter(responseBody)
                    }

                } else {
                    Toast.makeText(context, "Failed to get addresses", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetAddressesResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to get addresses: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun showAddressDialog() {
        val binding = AddressDialogboxBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
        dialog.show()

        binding.btnSubmit.setOnClickListener {
            locationName = binding.inpLocationName.text.toString()
            fullAddress = binding.inpFullAddress.text.toString()
            val userID = SharedPreferencesManager.getString(SharedPreferencesManager.USER_ID)

            val apiRequest = AddAddressRequest(userID!!.toInt(), locationName, fullAddress)

            val call: Call<AddAddressResponse> = apiServices.addAddress(apiRequest)

            call.enqueue(object : Callback<AddAddressResponse> {
                override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Address added successfully", Toast.LENGTH_LONG).show()
                        getAddresses()
                    } else {
                        Toast.makeText(context, "Failed to add address", Toast.LENGTH_LONG).show()
                    }
                    dialog.dismiss()
                }

                override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to add address: ${t.message}", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            })

        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initAdapter(addresses: List<Address>) {
        val adapter = DeliveryPageAdapter(addresses, this, viewLifecycleOwner)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}
