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

        back_to_login.setOnClickListener {
            Log.d("LoginActivity", "Try to show Main activity")
            //Launch the login activty
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }
    }
}