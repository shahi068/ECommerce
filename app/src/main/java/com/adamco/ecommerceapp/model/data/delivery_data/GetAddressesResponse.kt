package com.adamco.ecommerceapp.model.data.delivery_data

data class GetAddressesResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)