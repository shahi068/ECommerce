package com.adamco.ecommerceapp.model.data.register_data

data class RegisterUserRequest(
    val email_id: String,
    val full_name: String,
    val mobile_no: String,
    val password: String
)