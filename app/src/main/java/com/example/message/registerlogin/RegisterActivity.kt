package com.example.message.registerlogin

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.message.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import com.example.message.models.User
import com.example.message.messages.LatestMessagesActivity
import java.util.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        reg_button.setOnClickListener {
            val email = email_edit_reg.text.toString()
            val password = password_edit_reg.text.toString()
            //If user left the fields empty
            if(email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill the empty fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //debugging messages
            Log.d("RegisterActivity", "Email is: " + email )
            Log.d( "RegisterActivity", "Password: $password" )
            //Firebase incoming
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    //else if success

                    Toast.makeText(this, "Registering....", Toast.LENGTH_SHORT).show()
                    Log.d("RegisterActivity", "Successful created using uid: ${it?.result?.user?.uid}")
                        Toast.makeText(this, "Registering....", Toast.LENGTH_SHORT).show()
                    uploadImageToFirebaseStorage()
                    Toast.makeText(this, "Registering....", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "failed to create user:${it.message}")
                    Toast.makeText(this, "Please check the Mail ID, and Password should have at least 6 alpha numerical", Toast.LENGTH_SHORT).show()

                }
        }
            //When tapped on the add photo
            reg_profile_pic.setOnClickListener {
                Log.d("RegisterActivity","Try to show photo selector")
                val intent= Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }
            //If we tap on Already have an account?
            already_have_an_account.setOnClickListener {
            Log.d("RegisterActivity","Try to show login activity")
            //Launch the login activty
            val intent = Intent( this, LoginActivity::class.java)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            // Redirecting to github when taped on Privacy policy
            val privacyPolicy = this.findViewById<TextView>(R.id.privacy_policy)
            privacyPolicy.setOnClickListener {
            val viewIntent = Intent("android.intent.action.VIEW",
            Uri.parse("https://github.com/Abhiram-DLVSS/Android-Messaging-App/blob/master/README.md"));
            startActivity(viewIntent)
            }
            // Redirecting to github when taped on Learn more
            val learn = this.findViewById<TextView>(R.id.learn)
            learn.setOnClickListener {
            val viewIntent = Intent("android.intent.action.VIEW",
            Uri.parse("https://github.com/Abhiram-DLVSS/Android-Messaging-App/blob/master/README.md"));
            startActivity(viewIntent)
            }
    }
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri== null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully uploaded image: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity","File location: $it")
                    saveUserToFireDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("RegisterActivity","Failed to select photo")
            }
    }
        var selectedPhotoUri: Uri? = null

    // To select a photo from the album
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data  != null) {
        Log.d("RegisterActivity", "Photo was selected")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            select_photo_imageview_register.setImageBitmap(bitmap)
            reg_profile_pic.alpha =0f
         //   val bitmapDrawable =BitmapDrawable(bitmap)
           // reg_profile_pic.setBackgroundDrawable(bitmapDrawable)
        }
        }
        private fun saveUserToFireDatabase(profileImageUrl: String){
            val uid = FirebaseAuth.getInstance().uid ?:""
            val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
            val user = User(
                uid,
                username_edit_reg.text.toString(),
                profileImageUrl
            )

            ref.setValue(user)
                .addOnSuccessListener {
                    Log.d( "RegisterActivity","Finally we save the user to Firebase Database")

                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }
                .addOnFailureListener {
                    Log.d("RegisterActivity","Failed to save user data to Firebase Database")
                }

        }
    }