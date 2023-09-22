package com.desire.learning.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.desire.learning.adapter.messageadapter
import com.desire.learning.databinding.ActivityChatBinding
import com.desire.learning.dataclasses.messageclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<messageclass>
    private lateinit var messageadapter1: messageadapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firestore = Firebase.firestore
        postArrayList = ArrayList<messageclass>()
        getData()
        binding.chatrecycleview.layoutManager = LinearLayoutManager(this)
        messageadapter1 = messageadapter(postArrayList, Firebase.auth.currentUser!!.uid)
        binding.chatrecycleview.adapter = messageadapter1
        auth = Firebase.auth

        val usernamee = intent.getStringExtra("name11")
        supportActionBar?.title = usernamee
    }

    fun sent(view: View) {
        val postMap = hashMapOf<String, Any>()  //hashmap oluşturduk
        postMap.put("yourmessage", binding.yourmessage.text.toString()) //istediğimiz verileri aldık
        postMap.put("userid", auth.currentUser!!.uid)
        postMap.put("date", com.google.firebase.Timestamp.now())

        firestore.collection("messages").add(postMap)
            .addOnSuccessListener { //firestoreye verileri yazdırdık

                binding.yourmessage.setText("")
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG)
                    .show()  //failure olursa ne yaptık ona baktık


            }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getData() {
        firestore.collection("messages").orderBy("date")
            .addSnapshotListener { value, error ->          //collectionname firebasedatabasede bulabilirsin
                if (error != null) {
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()

                } else {
                    if (value != null) {
                        if (!value.isEmpty) {  //değer varsa
                            val documents = value.documents
                            postArrayList.clear()
                            for (i in documents) {
                                Log.d("ChatActivity", "${i.get("yourmessage")} Msg")
                                val yourmessage: String? = i.getString("yourmessage")
                                val userid = i.getString("userid")
                                val post = messageclass(
                                    yourmessage ?: "",
                                    userid
                                )       //açtığımız data class a yazdırıyoruz

                                println(yourmessage)
                                println(userid)
                                postArrayList.add(post) //açtığımız data class ı array liste ekliyoruz,recycleview için
                            }
                            messageadapter1.notifyDataSetChanged()
                            binding.chatrecycleview.scrollToPosition(postArrayList.size - 1)
                        }

                    }
                }
            }

    }

}