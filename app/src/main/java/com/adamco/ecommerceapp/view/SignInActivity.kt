package com.adamco.ecommerceapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adamco.ecommerceapp.SharedPreferencesManager
import com.adamco.ecommerceapp.databinding.ActivitySignInBinding
import com.adamco.ecommerceapp.model.data.login_data.LoginUserRequest
import com.adamco.ecommerceapp.model.data.login_data.LoginUserSuccessResponse
import com.adamco.ecommerceapp.model.remote.ApiClient
import com.adamco.ecommerceapp.model.remote.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var email : String
    private lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesManager.init(this)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        checkIsUserLoggedIn()
        with(binding) {
            btnSignin.setOnClickListener {
                email = emailInput.text.toString()
                password = passInput.text.toString()

                if (!isValidEmail(email)) {
                    Toast.makeText(this@SignInActivity, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    Toast.makeText(this@SignInActivity, "Please enter a valid password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                loginApi()
            }

            returnhome.setOnClickListener {
                val returnIntent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(returnIntent)
            }

            needAccount.setOnClickListener {
                val registerIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }

    private fun loginApi() {
        val loginUserRequest = LoginUserRequest(email, password)
        val apiServices = ApiClient.retrofit.create(ApiServices::class.java)
        val call: Call<LoginUserSuccessResponse> = apiServices.postLogin(loginUserRequest)

        call.enqueue(object : Callback<LoginUserSuccessResponse> {
            override fun onResponse(call: Call<LoginUserSuccessResponse>, response: Response<LoginUserSuccessResponse>) {
                if (response.isSuccessful) {
                    val loginSuccessResponse = response.body()
                    if (loginSuccessResponse != null) {
                        if (loginSuccessResponse.status == 0) {
                            Toast.makeText(
                                this@SignInActivity,
                                loginSuccessResponse.message ?: "Success",
                                Toast.LENGTH_LONG
                            ).show()
                            saveUserInfo(email, password)

                            val userIDStored = response.body()!!.user.user_id
                            SharedPreferencesManager.saveString(SharedPreferencesManager.USER_ID, userIDStored)

                        } else {
                            Toast.makeText(
                                this@SignInActivity,
                                loginSuccessResponse.message ?: "Login failed, please retry.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Unexpected response from server.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SignInActivity,
                        "Login failed: ${response.errorBody()?.string() ?: "Unknown error"}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginUserSuccessResponse>, t: Throwable) {
                Toast.makeText(
                    this@SignInActivity,
                    t.message ?: "Login failed, please retry.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && (email.contains(".com") || email.contains(".net") || email.contains(".org") || email.contains(".edu"))
    }

    private fun saveUserInfo(email: String, password: String) {
        SharedPreferencesManager.saveString(SharedPreferencesManager.USER_EMAIL, email)
        SharedPreferencesManager.saveString(SharedPreferencesManager.PASSWORD, password)
        SharedPreferencesManager.saveBoolean(SharedPreferencesManager.IS_LOGGED_IN, true)
        SharedPreferencesManager.saveBooleanAndGetStatus(
            SharedPreferencesManager.IS_LOGGED_IN,
            true
        )
        moveToDashboard()
    }

    private fun checkIsUserLoggedIn() {
        val isLoggedIn = SharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_LOGGED_IN)
        if (isLoggedIn) {
            moveToDashboard()
        }
    }

    private fun moveToDashboard() {
        val dashIntent = Intent(this@SignInActivity, Dashboard::class.java)
        startActivity(dashIntent)
        finish()
    }
}
