package com.desire.learning.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.desire.learning.R
import com.desire.learning.adapter.useradapter
import com.desire.learning.databinding.ActivityMainBinding
import com.desire.learning.dataclasses.users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var postArrayList: ArrayList<users>

    private lateinit var firestore: FirebaseFirestore

    private lateinit var useradapter2: useradapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        postArrayList = ArrayList<users>()

        upload()

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        useradapter2 = useradapter(postArrayList)
        binding.recycleView.adapter = useradapter2

        supportActionBar?.title= "DCY NEW WAY TO MESSAGİNG"

    }


    @SuppressLint("SuspiciousIndentation")
    fun upload(){

        firestore.collection("name and useremail").addSnapshotListener { value, error ->          //collectionname firebasedatabasede bulabilirsin
            if (error !=null){
                Toast.makeText(this, error.localizedMessage,Toast.LENGTH_LONG).show()

            }else{
                if(value != null){
                    if(!value.isEmpty){  //değer varsa
                        val documents = value.documents
                        for (i in documents){
                            val name= i.get("name") as String
                            val userEmail= i.get("userEmail") as String          //çektiğin verilerin adlarının doğru olduğuna emin ol
                            //val downloadUrl= i.get("downloadUrl") as String
                            if(userEmail != auth.currentUser?.email) {
                                val post = users(name,userEmail)
                                postArrayList.add(post)

                            }
                             //açtığımız data class ı array liste ekliyoruz,recycleview için
                        }
                        useradapter2.notifyDataSetChanged()

                    }

                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater

        menuInflater.inflate(R.menu.menu1,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu1) {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}


