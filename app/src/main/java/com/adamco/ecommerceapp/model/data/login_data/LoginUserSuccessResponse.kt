package com.adamco.ecommerceapp.model.data.login_data

data class LoginUserSuccessResponse(
    val message: String,
    val status: Int,
    val user: User
)