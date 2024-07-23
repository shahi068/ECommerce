package com.adamco.ecommerceapp

import android.view.ViewGroup
import com.adamco.ecommerceapp.data.product_data.Subcategory
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.data.product_data.Category
import com.adamco.ecommerceapp.databinding.DashboardItemBinding
import com.adamco.ecommerceapp.databinding.SubcategoryItemBinding
import com.adamco.ecommerceapp.remote.ApiClient
import com.adamco.ecommerceapp.remote.ApiServices
import com.squareup.picasso.Picasso

class SubcategoryAdapter(private val subcategory: List<Subcategory>) : RecyclerView.Adapter<SubcategoryItemViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryItemViewholder {
        val binding = SubcategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubcategoryItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoryItemViewholder, position: Int) {
        holder.bindData(subcategory[position])
    }

    override fun getItemCount() = subcategory.size


}

