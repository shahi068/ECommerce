package com.adamco.ecommerceapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.data.product_data.Category
import com.adamco.ecommerceapp.databinding.DashboardItemBinding
import com.squareup.picasso.Picasso

class DashboardAdapter(private val items: List<Category>) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private val baseUrl = "https://apolisrises.co.in/myshop/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = DashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class DashboardViewHolder(private val binding: DashboardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dashItem: Category) {
            with(binding) {
                txtItem.text = dashItem.category_name
                val imageUrl = baseUrl + dashItem.category_image_url
                Log.d("DashboardAdapter", "Loading image URL: $imageUrl")
                Picasso.get().load(imageUrl).into(itemImg)

                entireItem.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, ItemsFromCategory::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}
