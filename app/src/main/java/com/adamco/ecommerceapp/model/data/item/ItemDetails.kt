package com.adamco.ecommerceapp.model.data.item

import androidx.annotation.DrawableRes

data class ItemDetails(
    val itemName : String,
    val itemDesc: String,
    @DrawableRes val itemImg : Int)
