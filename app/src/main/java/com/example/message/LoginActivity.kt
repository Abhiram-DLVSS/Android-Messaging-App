package com.example.message

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {  }
                .addOnFailureListener {  }

        }

        back_to_reg.setOnClickListener {
            Log.d("back to reg","show main activity")
            Log.d("MainActivity", "Email is: " )
            finish()//back to main activity
        }
        // Redirecting to github when taped on Privacy policy
        val privacyPolicy = this.findViewById<TextView>(R.id.privacy_policy2)
        privacyPolicy.setOnClickListener {
            val viewIntent = Intent("android.intent.action.VIEW",
                Uri.parse("https://github.com/Abhiram-DLVSS/Android-Messaging-App/blob/master/README.md"));
            startActivity(viewIntent)
        }
    }
}