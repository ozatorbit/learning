package com.desire.learning.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.desire.learning.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login2)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        firestore = Firebase.firestore

        val currentuser = auth.currentUser
        if (currentuser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }


    @SuppressLint("SuspiciousIndentation")
    fun login(view: View) {
        val email = binding.email1.text.toString()
        val password = binding.password1.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please Write Email And Password", Toast.LENGTH_LONG).show()

        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }

        }
    }

    fun signup(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}