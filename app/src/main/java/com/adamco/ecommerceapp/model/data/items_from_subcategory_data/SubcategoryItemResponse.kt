package com.adamco.ecommerceapp.model.data.items_from_subcategory_data

data class SubcategoryItemResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)