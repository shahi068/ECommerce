package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adamco.ecommerceapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

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
                val email = emailInput.text.toString()
                val password = passInput.text.toString()

                if (!isValidEmail(email)) {
                    Toast.makeText(this@SignInActivity, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    Toast.makeText(this@SignInActivity, "Please enter a valid password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                saveUserInfo(email, password)
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

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && (email.contains(".com") || email.contains(".net") || email.contains(".org") || email.contains(".edu"))
    }

    private fun saveUserInfo(email: String, password: String) {
        SharedPreferencesManager.saveString(SharedPreferencesManager.USER_EMAIL, email)
        SharedPreferencesManager.saveString(SharedPreferencesManager.PASSWORD, password)
        SharedPreferencesManager.saveBoolean(SharedPreferencesManager.IS_LOGGED_IN, true)
        SharedPreferencesManager.saveBooleanAndGetStatus(SharedPreferencesManager.IS_LOGGED_IN, true)
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
