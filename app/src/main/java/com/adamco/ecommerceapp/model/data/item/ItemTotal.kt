package com.adamco.ecommerceapp.model.data.item

data class ItemTotal(
    val itemID: Long = 0,
    val name: String,
    val description: String,
    val itemPrice: Double,
    var itemQuantity: Int,
    val itemImg: String
)
