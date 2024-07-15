package com.adamco.ecommerceapp.data

data class LoginUserSuccessResponse(
    val message: String,
    val status: Int,
    val user: User
)