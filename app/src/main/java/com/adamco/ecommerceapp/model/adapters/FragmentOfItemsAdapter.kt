package com.adamco.ecommerceapp.model.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.view.SubcategoryActivity
import com.adamco.ecommerceapp.databinding.DashboardItemBinding
import com.adamco.ecommerceapp.databinding.FragmentItemBinding
import com.adamco.ecommerceapp.model.data.items_from_subcategory_data.Product
import com.adamco.ecommerceapp.model.data.product_data.Category
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import com.squareup.picasso.Picasso

class FragmentOfItemsAdapter(private val items: List<Product>) : RecyclerView.Adapter<FragmentOfItemsAdapter.FragmentOfItemsViewHolder>() {
    val apiServices : ApiServices = ApiClient.retrofit.create(ApiServices::class.java)
    private val baseUrl = "https://apolisrises.co.in/myshop/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentOfItemsViewHolder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FragmentOfItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FragmentOfItemsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class FragmentOfItemsViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val baseUrl = "https://apolisrises.co.in/myshop/images/"
        fun bind(fragmentItem: Product) {
            with(binding) {
                txtItem.text = fragmentItem.category_name
                txtItemDesc.text = fragmentItem.description
                txtRating.text = fragmentItem.average_rating
                txtItemPrice.text = fragmentItem.price

                val imageUrl = baseUrl + fragmentItem.product_image_url
                Log.d("FragmentAdapter", "Loading image URL: $imageUrl")
                Picasso.get().load(imageUrl).into(itemImg)



            }
        }
    }
}
