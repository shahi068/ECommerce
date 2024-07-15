package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adamco.ecommerceapp.data.RegisterUserRequest
import com.adamco.ecommerceapp.data.RegisterUserResponse
import com.adamco.ecommerceapp.databinding.ActivitySignUpBinding
import com.adamco.ecommerceapp.remote.ApiClient
import com.adamco.ecommerceapp.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            returnhome.setOnClickListener {
                val returnIntent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(returnIntent)
            }
            btnSignup.setOnClickListener {
                if (!nameInput.text.any { it.isDigit() }) {
                    name = nameInput.text.toString()
                } else {
                    val invalidName = Toast.makeText(this@SignUpActivity, "Name should not contain numbers", Toast.LENGTH_SHORT)
                    invalidName.show()
                    return@setOnClickListener
                }

                phoneNumber = phoneInput.text.toString()

                if (emailInput.text.contains("@") && (emailInput.text.contains(".com") || emailInput.text.contains(".net") || emailInput.text.contains(".org") || emailInput.text.contains(".edu"))) {
                    email = emailInput.text.toString()
                } else {
                    val validEmail = Toast.makeText(this@SignUpActivity, "Please enter a valid email address", Toast.LENGTH_SHORT)
                    validEmail.show()
                    return@setOnClickListener
                }

                if (passInput.text.isNotEmpty()) {
                    if (passInput.text.toString() == confirmPass.text.toString()) {
                        password = passInput.text.toString()
                    } else {
                        val validConfirm = Toast.makeText(this@SignUpActivity, "Passwords must match, please try again.", Toast.LENGTH_SHORT)
                        validConfirm.show()
                        return@setOnClickListener
                    }
                } else {
                    val validPass = Toast.makeText(this@SignUpActivity, "Please enter a valid password", Toast.LENGTH_SHORT)
                    validPass.show()
                    return@setOnClickListener
                }

                registerUser()
            }
        }
    }

    private fun registerUser() {
        val apiServices = ApiClient.retrofit.create(ApiServices::class.java)
        val apiRequest = RegisterUserRequest(email, name, phoneNumber, password)
        val call: Call<RegisterUserResponse> = apiServices.postRegistration(apiRequest)

        call.enqueue(object : Callback<RegisterUserResponse>{
            override fun onResponse(call: Call<RegisterUserResponse>, response: Response<RegisterUserResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SignUpActivity,
                        response.body()?.message ?: "Success",
                        Toast.LENGTH_LONG
                    ).show()
                    val dashIntent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    startActivity(dashIntent)
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Unknown error. Error code: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegisterUserResponse>, thrwble: Throwable) {
                thrwble.printStackTrace()
                Toast.makeText(
                    this@SignUpActivity,
                    "Unknown error. Please retry.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        )

    }
}
