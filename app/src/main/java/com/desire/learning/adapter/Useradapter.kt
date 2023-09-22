package com.desire.learning.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desire.learning.view.ChatActivity
import com.desire.learning.databinding.UsersLayoutBinding
import com.desire.learning.dataclasses.users


class useradapter(private val userList : ArrayList<users>) : RecyclerView.Adapter<useradapter.UserHolder>() {
    class UserHolder(val binding: UsersLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = UsersLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.nametxt.text = userList[position].name

        holder.binding.nametxt.setOnClickListener{
            val context = it.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name11",userList[position].name)
            context.startActivity(intent)
        }
    }
}
