package com.phi1app.ui.model

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long,
    val isTyping: Boolean = false
)
