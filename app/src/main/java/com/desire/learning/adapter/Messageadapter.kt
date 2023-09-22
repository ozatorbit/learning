package com.desire.learning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desire.learning.databinding.ReceiveBinding
import com.desire.learning.databinding.SentBinding
import com.desire.learning.dataclasses.messageclass

class messageadapter(private val MessageList :ArrayList<messageclass>, private val currentUserId: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageHolder(val binding: SentBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceivedMessageHolder(val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        val message = MessageList[position]
        return if (message.userId == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = SentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SentMessageHolder(binding)
        } else {
            val binding = ReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ReceivedMessageHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = MessageList[position]
        if (holder is SentMessageHolder) {
            holder.binding.sentmessagetext.text = message.yourmessage
        } else if (holder is ReceivedMessageHolder) {
            holder.binding.receivemessagetext.text = message.yourmessage  // receivedmessagetext TextView'inizin adı
        }
    }

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }
}