package com.desire.learning.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.desire.learning.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        firestore= Firebase.firestore
    }

    @SuppressLint("SuspiciousIndentation")
    fun signup1(view: View){
        val email= binding.email1.text.toString()
        val password= binding.password1.text.toString()
        if (email.equals("") || password.equals("")){
            Toast.makeText(this,"Please Write Email And Password",Toast.LENGTH_LONG).show()

        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                Toast.makeText(this,"You Sign Up Successfully!",Toast.LENGTH_LONG).show()
                val postMap = hashMapOf<String,Any>()
                //postMap.put("downloadUrl",downloadUrl)


                postMap.put("name",binding.name.text.toString())
                postMap.put("userEmail",auth.currentUser!!.email!!)
                postMap.put("date",com.google.firebase.Timestamp.now())

                firestore.collection("name and useremail").add(postMap).addOnSuccessListener {
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()


                }

                val intent =Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }


    }


}
