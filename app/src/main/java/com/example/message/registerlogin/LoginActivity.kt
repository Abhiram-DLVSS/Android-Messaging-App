package com.example.message.registerlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.message.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.example.message.messages.LatestMessagesActivity

class LoginActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        regd_login.setOnClickListener {
            val email =regd_email.text.toString()
            val password = regd_password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            }

            Log.d("Login", "Attempt to login with email/pw: $email/***")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")

                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent) }
                .addOnFailureListener { Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()  }

        }

        back_to_reg.setOnClickListener {
            Log.d("back to reg","show main activity")
            Log.d("RegisterActivity", "Email is: " )
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