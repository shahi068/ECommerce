package com.adamco.ecommerceapp.data

data class RegisterUserRequest(
    val email_id: String,
    val full_name: String,
    val mobile_no: String,
    val password: String
)