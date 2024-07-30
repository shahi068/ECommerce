package com.adamco.ecommerceapp.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.FragmentItemBinding
import com.adamco.ecommerceapp.model.data.item.ItemTotal
import com.adamco.ecommerceapp.model.data.items_from_subcategory_data.Product
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import com.squareup.picasso.Picasso

class FragmentOfItemsAdapter(private val items: List<Product>, private val databaseHelper: DatabaseHelper) : RecyclerView.Adapter<FragmentOfItemsAdapter.FragmentOfItemsViewHolder>() {
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
                txtItem.text = fragmentItem.product_name
                txtItemDesc.text = fragmentItem.description
                txtRating.text = fragmentItem.average_rating
                txtItemPrice.text = fragmentItem.price

                val imageUrl = baseUrl + fragmentItem.product_image_url
                Log.d("FragmentAdapter", "Loading image URL: $imageUrl")
                Picasso.get().load(imageUrl).into(itemImg)


                txtAddToCart.setOnClickListener {
                    val existingItem = databaseHelper.getItemByName(fragmentItem.product_name)
                    if (existingItem != null) {
                        // If the item exists, increment the quantity
                        existingItem.itemQuantity += 1
                        databaseHelper.updateItem(existingItem)
                        Toast.makeText(binding.root.context, "${existingItem.name} quantity updated in cart", Toast.LENGTH_SHORT).show()
                    } else {
                        // If the item does not exist, create a new record
                        val newItem = ItemTotal(
                            name = fragmentItem.product_name,
                            description = fragmentItem.description,
                            itemPrice = fragmentItem.price.toDouble(),
                            itemQuantity = 1,
                            itemImg = imageUrl
                        )
                        val id = databaseHelper.insertData(newItem)
                        if (id > -1) {
                            Toast.makeText(binding.root.context, "${newItem.name} added to cart", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(binding.root.context, "Error adding ${newItem.name} to cart", Toast.LENGTH_SHORT).show()
                        }
                    }
                }



            }
        }
    }
}
