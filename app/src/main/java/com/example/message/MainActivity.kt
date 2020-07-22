package com.example.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg_button.setOnClickListener {
            val email = email_edit_reg.text.toString()
            val password = password_edit_reg.text.toString()
            if(email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please enter text in empty fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("MainActivity", "Email is: " + email )
            Log.d( "MainActivity", "Password: $password" )
            //Firebase incoming
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    //ele if success
                    Log.d("Main", "Successful created using uid: ${it?.result?.user?.uid}")
                }
                .addOnFailureListener {
                    Log.d("Main", "failed to create user:${it.message}")
                }
        }
        already_have_an_account.setOnClickListener {
            Log.d("MainActivity","Try to show login activity")
            //Launch the login activty
            val intent = Intent( this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}