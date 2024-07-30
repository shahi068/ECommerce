package com.adamco.ecommerceapp.view.fragments_for_checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamco.ecommerceapp.R
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.FragmentCheckoutPageBinding
import com.adamco.ecommerceapp.model.adapters.CheckingOutPageAdapter


class CheckoutPageFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutPageBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var checkingoutAdapter: CheckingOutPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        updateTotalPrice() // Initial total price calculation
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnNext.setOnClickListener {
                (activity as? CheckingOutTabLayout)?.switchToNextTab()
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
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
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
