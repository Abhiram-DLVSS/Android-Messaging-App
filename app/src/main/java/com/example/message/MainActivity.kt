package com.example.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg_button.setOnClickListener {
            val email = email_edit_reg.text.toString()
            val password = password_edit_reg.text.toString()

            Log.d("MainActivity", "Email is: " + email )
            Log.d( "MainActivity", "Password: $password" )
        }
        already_have_an_account.setOnClickListener {
            Log.d("MainActivity","Try to show login activity")
            //Launch the login activty
            val intent = Intent( this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}