package com.adamco.ecommerceapp.model.data.product_data

data class CategoryRequestResponse(
    val status: Int,
    val message: String,
    val categories: List<Category>
)