package com.adamco.ecommerceapp.data.product_data

data class SubcategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)