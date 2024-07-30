package com.adamco.ecommerceapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.DatabaseHelper
import com.adamco.ecommerceapp.databinding.ActivityShoppingCartBinding
import com.adamco.ecommerceapp.model.data.item.ItemTotal
import com.squareup.picasso.Picasso

class ShoppingCartAdapter(
    private val items: MutableList<ItemTotal>,
    private val databaseHelper: DatabaseHelper,
    private val onCartUpdated: (Double) -> Unit
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val binding = ActivityShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ShoppingCartViewHolder(private val binding: ActivityShoppingCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemTotal) {
            with(binding) {
                txtItem.text = item.name
                Picasso.get().load(item.itemImg).into(itemImg)
                txtItemDesc.text = item.description
                txtItemPriceUpdate.text = item.itemPrice.toString()
                txtItemCount.text = item.itemQuantity.toString()
                txtTotalPrice.text = (item.itemPrice * item.itemQuantity).toString()

                itemAdd.setOnClickListener {
                    item.itemQuantity += 1
                    databaseHelper.updateItem(item)
                    txtItemCount.text = item.itemQuantity.toString()
                    txtTotalPrice.text = (item.itemPrice * item.itemQuantity).toString()
                    onCartUpdated(calculateTotalBill()) // Update total price
                }

                itemDelete.setOnClickListener {
                    if (item.itemQuantity > 1) {
                        item.itemQuantity -= 1
                        databaseHelper.updateItem(item)
                        txtItemCount.text = item.itemQuantity.toString()
                        txtTotalPrice.text = (item.itemPrice * item.itemQuantity).toString()
                        onCartUpdated(calculateTotalBill())
                    } else {
                        val deleted = databaseHelper.deleteItem(item.itemID)
                        if (deleted > 0) {
                            Toast.makeText(binding.root.context, "${item.name} removed from cart", Toast.LENGTH_SHORT).show()
                            items.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            onCartUpdated(calculateTotalBill())
                        } else {
                            Toast.makeText(binding.root.context, "Error removing ${item.name} from cart", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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
