package com.adamco.ecommerceapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adamco.ecommerceapp.SharedPreferencesManager
import com.adamco.ecommerceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesManager.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIsUserLoggedIn() // Check if the user is logged in and redirect if necessary
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnSignin.setOnClickListener {
                val loginIntent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(loginIntent)
            }
            btnSignup.setOnClickListener {
                val signUpIntent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(signUpIntent)
            }
        }
    }

    private fun checkIsUserLoggedIn() {
        val isLoggedIn = SharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_LOGGED_IN)
        if (isLoggedIn) {
            moveToDashboard()
        }
    }

    private fun moveToDashboard() {
        val dashIntent = Intent(this@MainActivity, Dashboard::class.java)
        startActivity(dashIntent)
        finish()
    }
}
