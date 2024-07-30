package com.adamco.ecommerceapp.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.CheckingoutItemDetailsBinding
import com.adamco.ecommerceapp.model.data.item.ItemTotal
import com.squareup.picasso.Picasso

class CheckingOutPageAdapter(
    private val items: MutableList<ItemTotal>,
    private val databaseHelper: DatabaseHelper,
    private val onCartUpdated: (Double) -> Unit
) : RecyclerView.Adapter<com.adamco.ecommerceapp.model.adapters.CheckingOutPageAdapter.CheckingOutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckingOutPageAdapter.CheckingOutViewHolder {
        val binding = CheckingoutItemDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckingOutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckingOutPageAdapter.CheckingOutViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class CheckingOutViewHolder(private val binding: CheckingoutItemDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemTotal) {
            with(binding) {
                txtItem.text = item.name
                Picasso.get().load(item.itemImg).into(itemImg)
                txtUnitPrice.text = item.itemPrice.toString()
                txtQuantity.text = item.itemQuantity.toString()
                txtTotalPrice.text = (item.itemPrice * item.itemQuantity).toString()

                // Update the total price in the parent activity
                onCartUpdated(calculateTotalBill())
            }
        }
    }

    private fun calculateTotalBill(): Double {
        var total = 0.00
        for (item in items) {
            total += item.itemPrice * item.itemQuantity
        }
        return total
    }
}
