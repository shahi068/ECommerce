package com.adamco.ecommerceapp.view.fragments_for_checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamco.ecommerceapp.databinding.FragmentPaymentPageBinding
import com.adamco.ecommerceapp.model.adapters.PaymentPageAdapter
import com.adamco.ecommerceapp.model.data.payment_data.PaymentViewModel

class PaymentPageFragment : Fragment() {
    private lateinit var binding: FragmentPaymentPageBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PaymentViewModel::class.java]
        initAdapter()
    }

    private fun initAdapter() {
        val listOfPaymentMethods = listOf("Cash On Delivery", "Internet Banking", "Debit card / Credit Card", "PayPal")
        val adapter = PaymentPageAdapter(listOfPaymentMethods, this, viewLifecycleOwner)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}
