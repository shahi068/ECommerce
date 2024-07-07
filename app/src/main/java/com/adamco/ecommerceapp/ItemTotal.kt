package com.adamco.ecommerceapp

data class ItemTotal(
    val itemID: Long = 0,
    val name: String,
    val description: String,
    val itemPrice: Double,
    var itemQuantity: Int,
    val itemImg: Int
)
