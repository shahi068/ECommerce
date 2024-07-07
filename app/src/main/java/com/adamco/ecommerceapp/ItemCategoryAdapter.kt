package com.adamco.ecommerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.databinding.ItemCategoryDetailsBinding

class ItemCategoryAdapter(
    private val context: Context,
    private val items: List<ItemCategoryDetails>,
    private val databaseHelper: DatabaseHelper
) : RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val binding = ItemCategoryDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ItemCategoryViewHolder(private val binding: ItemCategoryDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemCategory: ItemCategoryDetails) {
            with(binding) {
                txtItem.text = itemCategory.itemName
                itemImg.setImageResource(itemCategory.itemImg)
                txtItemDesc.text = itemCategory.itemDesc
                txtRating.text = itemCategory.itemRating.toString()
                txtItemPriceUpdate.text = "200" // Assuming a default price, this should be dynamically set based on your data

                txtAddtoCart.setOnClickListener {
                    val existingItem = databaseHelper.getItemByName(itemCategory.itemName)
                    if (existingItem != null) {
                        // If the item exists, increment the quantity
                        existingItem.itemQuantity += 1
                        databaseHelper.updateItem(existingItem)
                        Toast.makeText(binding.root.context, "${existingItem.name} quantity updated in cart", Toast.LENGTH_SHORT).show()
                    } else {
                        // If the item does not exist, create a new record
                        val newItem = ItemTotal(
                            name = itemCategory.itemName,
                            description = itemCategory.itemDesc,
                            itemPrice = 200.0,
                            itemQuantity = 1,
                            itemImg = itemCategory.itemImg
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
