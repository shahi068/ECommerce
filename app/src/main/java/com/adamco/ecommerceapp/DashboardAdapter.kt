package com.adamco.ecommerceapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.databinding.DashboardItemBinding

class DashboardAdapter(private val items: List<DashboardItem>) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = DashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class DashboardViewHolder(private val binding: DashboardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dashItem: DashboardItem) {
            with(binding) {
                txtItem.text = dashItem.item
                itemImg.setImageResource(dashItem.icon)

                entireItem.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, ItemsFromCategory::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}
