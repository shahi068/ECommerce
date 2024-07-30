package com.adamco.ecommerceapp.model.data.delivery_data

data class AddAddressRequest(
    val user_id: Int,
    val title: String,
    val address: String
)