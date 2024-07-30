package com.adamco.ecommerceapp.model.remote

import com.adamco.ecommerceapp.model.data.delivery_data.AddAddressRequest
import com.adamco.ecommerceapp.model.data.delivery_data.AddAddressResponse
import com.adamco.ecommerceapp.model.data.delivery_data.GetAddressesResponse
import com.adamco.ecommerceapp.model.data.items_from_subcategory_data.SubcategoryItemResponse
import com.adamco.ecommerceapp.model.data.login_data.LoginUserRequest
import com.adamco.ecommerceapp.model.data.login_data.LoginUserSuccessResponse
import com.adamco.ecommerceapp.model.data.product_data.CategoryRequestResponse
import com.adamco.ecommerceapp.model.data.product_data.SubcategoryResponse
import com.adamco.ecommerceapp.model.data.register_data.RegisterUserRequest
import com.adamco.ecommerceapp.model.data.register_data.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("SubCategory/products/{sub_category_id}")
    fun getItemsFromSubcategories(@Path("sub_category_id") subCategoryId : String) : Call<SubcategoryItemResponse>

    @POST("User/address")
    fun addAddress(@Body addAddressRequest : AddAddressRequest) : Call<AddAddressResponse>

    @GET("User/addresses/{user_id}")
    fun getAddresses(@Path("user_id") userID : Int) : Call<GetAddressesResponse>
}