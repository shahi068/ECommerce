package com.adamco.ecommerceapp.model.adapters

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adamco.ecommerceapp.databinding.SubcategoryItemBinding
import com.adamco.ecommerceapp.model.data.product_data.Subcategory
import com.adamco.ecommerceapp.view.SubcategoryActivity
import com.squareup.picasso.Picasso

class SubcategoryItemViewholder(val binding: SubcategoryItemBinding): ViewHolder(binding.root) {

    fun bindData(subcategory: Subcategory){
        with(binding) {
            txtSubCategoryName.text = subcategory.subcategory_name
            val baseUrl = "https://apolisrises.co.in/myshop/images/"
            val imageUrl = baseUrl + subcategory.subcategory_image_url
            Picasso.get().load(imageUrl).into(imgSubCategory)

            entireItem.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, SubcategoryActivity::class.java)
                intent.putExtra("subcategory_id", subcategory.subcategory_id)
                context.startActivity(intent)
            }
        }
    }
}