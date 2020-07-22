package com.example.message

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        regd_login.setOnClickListener {
            val email =regd_email.text.toString()
            val password = regd_password.text.toString()
            Log.d("Login", "Attempt to login with email/pw: $email/***")

        }

        back_to_reg.setOnClickListener {
            Log.d("back to reg","show main activity")
            Log.d("MainActivity", "Email is: " )
            finish()
        }
    }
}