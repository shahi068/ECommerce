package com.adamco.ecommerceapp.view.fragments_for_checkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adamco.ecommerceapp.databinding.ActivityCheckingOutTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckingOutTabLayout : AppCompatActivity() {
    private lateinit var binding : ActivityCheckingOutTabLayoutBinding
    private lateinit var adapter: AuthVPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckingOutTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val fragments = listOf(
            CheckoutPageFragment(),
            DeliveryPageFragment(),
            PaymentPageFragment(),
            SummaryPageFragment()
        )

        adapter = AuthVPAdapter(fragments, this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Cart Items"
                1 -> tab.text = "Delivery"
                2 -> tab.text = "Payment"
                3 -> tab.text = "Summary"
            }
        }.attach()
    }

    fun switchToNextTab() {
        val currentItem = binding.viewPager.currentItem
        if (currentItem < adapter.itemCount - 1) {
            binding.viewPager.currentItem = currentItem + 1
        }
    }
}
