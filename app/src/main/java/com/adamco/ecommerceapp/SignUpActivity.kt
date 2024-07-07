package com.adamco.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adamco.ecommerceapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding){
            returnhome.setOnClickListener{
                val returnIntent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(returnIntent)
            }
            btnSignup.setOnClickListener {
                if((emailInput.text.contains("@")) && (emailInput.text.contains(".com") || emailInput.text.contains(".net") || emailInput.text.contains(".org") || emailInput.text.contains(".edu")))
                else{
                    val validEmail = Toast.makeText(this@SignUpActivity, "Please enter a valid email address", Toast.LENGTH_SHORT)
                    validEmail.show()
                }

                if((passInput.text.isNotEmpty()))
                else{
                    val validPass = Toast.makeText(this@SignUpActivity, "Please enter a valid password", Toast.LENGTH_SHORT)
                    validPass.show()
                }

                if(passInput.text.toString() != confirmPass.text.toString()){
                    val validConfirm = Toast.makeText(this@SignUpActivity, "Passwords must match, please try again.", Toast.LENGTH_SHORT)
                    validConfirm.show()
                }
            }
        }
    }
}