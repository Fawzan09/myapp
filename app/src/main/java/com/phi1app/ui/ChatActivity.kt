package com.phi1app.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.phi1app.Phi1Application
import com.phi1app.databinding.ActivityChatBinding
import com.phi1app.ui.adapter.ChatAdapter
import com.phi1app.ui.model.ChatMessage
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityChatBinding
    private lateinit var app: Phi1Application
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as Phi1Application
        
        setupUI()
        setupChat()
        addWelcomeMessage()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Phi-1 AI Assistant"
        
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    
    private fun setupChat() {
        chatAdapter = ChatAdapter(chatMessages)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
        
        binding.sendButton.setOnClickListener {
            sendMessage()
        }
        
        binding.messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else {
                false
            }
        }
    }
    
    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            text = "Hello! I'm Phi-1, your local AI assistant. I'm running entirely on your device, so your conversations are private and secure. How can I help you today?",
            isUser = false,
            timestamp = System.currentTimeMillis()
        )
        chatMessages.add(welcomeMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        scrollToBottom()
    }
    
    private fun sendMessage() {
        val messageText = binding.messageEditText.text.toString().trim()
        if (messageText.isEmpty()) return
        
        // Add user message
        val userMessage = ChatMessage(
            text = messageText,
            isUser = true,
            timestamp = System.currentTimeMillis()
        )
        chatMessages.add(userMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        scrollToBottom()
        
        // Clear input
        binding.messageEditText.text.clear()
        
        // Show typing indicator
        showTypingIndicator()
        
        // Generate AI response
        lifecycleScope.launch {
            try {
                val response = app.modelManager.generateText(messageText)
                hideTypingIndicator()
                
                val aiMessage = ChatMessage(
                    text = response,
                    isUser = false,
                    timestamp = System.currentTimeMillis()
                )
                chatMessages.add(aiMessage)
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                scrollToBottom()
                
            } catch (e: Exception) {
                hideTypingIndicator()
                val errorMessage = ChatMessage(
                    text = "Sorry, I encountered an error. Please try again.",
                    isUser = false,
                    timestamp = System.currentTimeMillis()
                )
                chatMessages.add(errorMessage)
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                scrollToBottom()
            }
        }
    }
    
    private fun showTypingIndicator() {
        val typingMessage = ChatMessage(
            text = "Phi-1 is thinking...",
            isUser = false,
            timestamp = System.currentTimeMillis(),
            isTyping = true
        )
        chatMessages.add(typingMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        scrollToBottom()
    }
    
    private fun hideTypingIndicator() {
        // Remove the last message if it's a typing indicator
        if (chatMessages.isNotEmpty() && chatMessages.last().isTyping) {
            chatMessages.removeAt(chatMessages.size - 1)
            chatAdapter.notifyItemRemoved(chatMessages.size)
        }
    }
    
    private fun scrollToBottom() {
        if (chatMessages.isNotEmpty()) {
            binding.recyclerView.smoothScrollToPosition(chatMessages.size - 1)
        }
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
