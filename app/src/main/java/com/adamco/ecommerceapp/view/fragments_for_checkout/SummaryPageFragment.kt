package com.adamco.ecommerceapp.view.fragments_for_checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.FragmentSummaryPageBinding
import com.adamco.ecommerceapp.model.adapters.CheckingOutPageAdapter
import com.adamco.ecommerceapp.model.adapters.DeliveryPageAdapter
import com.adamco.ecommerceapp.model.data.delivery_data.Address
import com.adamco.ecommerceapp.model.data.delivery_data.DeliveryViewModel
import com.adamco.ecommerceapp.model.data.payment_data.PaymentViewModel

class SummaryPageFragment : Fragment() {
    private lateinit var binding: FragmentSummaryPageBinding
    private lateinit var deliveryViewModel: DeliveryViewModel
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var addressList: MutableList<Address>
    private lateinit var checkingoutAdapter: CheckingOutPageAdapter
    private lateinit var deliveryAdapter: DeliveryPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deliveryViewModel = ViewModelProvider(requireActivity())[DeliveryViewModel::class.java]
        paymentViewModel = ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
        addressList = mutableListOf()

        initCartRecyclerView()
        initAddressRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        Log.d("SummaryPageFragment", "Setting up observers")

        deliveryViewModel.finalAddress.observe(viewLifecycleOwner) { address ->
            Log.d("SummaryPageFragment", "Observed address: $address")
            addressList.clear() // Ensure only the selected address is displayed
            addressList.add(address)
            deliveryAdapter.notifyDataSetChanged()
            Log.d("SummaryPageFragment", "Address list updated: $addressList")
        }

        paymentViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            Log.d("SummaryPageFragment", "Observed payment method: $method")
            binding.actualPaymentOption.text = method
            Log.d("SummaryPageFragment", "Updated payment method text: ${binding.actualPaymentOption.text}")
        }
    }

    private fun initCartRecyclerView() {
        val databaseHelper = DatabaseHelper(requireContext())
        val itemList = databaseHelper.readData().toMutableList()
        checkingoutAdapter = CheckingOutPageAdapter(
            itemList,
            databaseHelper
        ) { totalBill ->
            updateTotalPrice(totalBill)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = checkingoutAdapter

        updateTotalPrice()
    }

    private fun initAddressRecyclerView() {
        deliveryAdapter = DeliveryPageAdapter(addressList, this, viewLifecycleOwner) // Pass both LifecycleOwner and ViewModelStoreOwner
        binding.addressRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.addressRecyclerView.adapter = deliveryAdapter
        Log.d("SummaryPageFragment", "Initialized address RecyclerView with adapter: $deliveryAdapter")
    }

    private fun updateTotalPrice(totalBill: Double? = null) {
        val total = totalBill ?: calculateTotalBill()
        binding.calcTotalBill.text = "$${total}"
    }

    private fun calculateTotalBill(): Double {
        val databaseHelper = DatabaseHelper(requireContext())
        val itemList = databaseHelper.readData()
        var total = 0.0
        for (item in itemList) {
            total += item.itemPrice * item.itemQuantity
        }
        return total
    }
}
