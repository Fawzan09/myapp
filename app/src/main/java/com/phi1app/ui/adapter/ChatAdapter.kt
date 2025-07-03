package com.phi1app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phi1app.databinding.ItemChatMessageBinding
import com.phi1app.ui.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    
    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }
    
    override fun getItemCount(): Int = messages.size
    
    inner class ChatViewHolder(private val binding: ItemChatMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(message: ChatMessage) {
            binding.messageText.text = message.text
            binding.timestampText.text = dateFormat.format(Date(message.timestamp))
            
            if (message.isUser) {
                // User message styling
                binding.messageContainer.setBackgroundResource(com.phi1app.R.drawable.bg_user_message)
                binding.messageText.setTextColor(binding.root.context.getColor(android.R.color.white))
                binding.userAvatar.visibility = View.VISIBLE
                binding.aiAvatar.visibility = View.GONE
                
                // Align to right
                val params = binding.messageContainer.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = 80
                params.rightMargin = 16
                binding.messageContainer.layoutParams = params
                
            } else {
                // AI message styling
                binding.messageContainer.setBackgroundResource(com.phi1app.R.drawable.bg_ai_message)
                binding.messageText.setTextColor(binding.root.context.getColor(android.R.color.black))
                binding.userAvatar.visibility = View.GONE
                binding.aiAvatar.visibility = View.VISIBLE
                
                // Align to left
                val params = binding.messageContainer.layoutParams as ViewGroup.MarginLayoutParams
                params.leftMargin = 16
                params.rightMargin = 80
                binding.messageContainer.layoutParams = params
                
                // Show typing animation if needed
                if (message.isTyping) {
                    binding.typingIndicator.visibility = View.VISIBLE
                } else {
                    binding.typingIndicator.visibility = View.GONE
                }
            }
        }
    }
}
