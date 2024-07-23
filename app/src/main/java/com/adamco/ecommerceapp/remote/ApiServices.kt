package com.adamco.ecommerceapp.remote

import com.adamco.ecommerceapp.data.LoginUserRequest
import com.adamco.ecommerceapp.data.LoginUserSuccessResponse
import com.adamco.ecommerceapp.data.RegisterUserRequest
import com.adamco.ecommerceapp.data.RegisterUserResponse
import com.adamco.ecommerceapp.data.product_data.CategoryRequestResponse
import com.adamco.ecommerceapp.data.product_data.SubcategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @Headers("Content-type: application/json")
    @POST("User/register")
    fun postRegistration(@Body registerUserRequest : RegisterUserRequest) : Call<RegisterUserResponse>


    @Headers("Content-type: application/json")
    @POST("User/auth")
    fun postLogin(@Body loginUserRequest : LoginUserRequest) : Call<LoginUserSuccessResponse>

    @GET("Category")
    fun getCategories() : Call<CategoryRequestResponse>

    @GET("SubCategory")
    fun getSubCategories(@Query("category_id") categoryId: String): Call<SubcategoryResponse>
}